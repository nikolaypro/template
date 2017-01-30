package com.mascot.service.controller.user;

import com.mascot.common.ErrorLogger;
import com.mascot.common.MailSender;
import com.mascot.common.MascotUtils;
import com.mascot.server.beans.UserService;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.common.ServerUtils;
import com.mascot.server.model.Role;
import com.mascot.server.model.User;
import com.mascot.service.common.CommonUtils;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.WebError;
import com.mascot.service.controller.common.AutocompleteType;
import com.mascot.service.controller.common.ResultRecord;
import com.mascot.service.controller.common.TableParams;
import com.mascot.service.controller.common.TableResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Nikolay on 25.11.2015.
 */
//@RestController
@Controller
@RequestMapping()
public class AuthenticationController extends AbstractController {
    @Inject
    private UserService userService;

    @Value("${autoLogin.login}")
    private String autoLogin;

    @Value("${autoLogin.password}")
    private String autoPassword;

    @Value("${productAutocompleteType}")
    private AutocompleteType productAutocompleteType;

    @Value("${report-group.enabled}")
    private Boolean reportGroupEnabled;

    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    @ResponseBody
    public LoginUserRecord authenticate(@RequestBody User user) {
        CommonUtils.checkRegistered();
        if (MascotUtils.isEmpty(user.getPassword())) {
            throw createInvalidPasswordException(user);
        }
        final User entityUser = userService.loadUserByLogin(user.getLogin());
        if (entityUser == null) {
            throw createInvalidUserNameException(user);
        }
        if (!user.getPassword().equals(entityUser.getPassword())) {
            throw createInvalidPasswordException(user);
        }
        logger.info("Successfully login: username = " + user.getLogin() +
                ", roles: [" + StringUtils.collectionToCommaDelimitedString(entityUser.getRoles()) + "]");
        return LoginUserRecord.build(entityUser, ServerUtils.getAppVersion(), productAutocompleteType, reportGroupEnabled);
    }

    @RequestMapping(path = "/autoLogin", method = RequestMethod.POST)
    @ResponseBody
    public AutoLoginRecord getAutoLogin() {
        return new AutoLoginRecord(autoLogin, autoPassword);
    }

    private RuntimeException createInvalidUserNameException(User user) {
        final String msg = "Invalid user name: '" + user.getLogin() + "'";
        logger.info(msg);
        return new AuthenticationCredentialsNotFoundException(msg);
    }

    private RuntimeException createInvalidPasswordException(User user) {
        final String msg = "Invalid password";
        logger.info(msg);
        return new AuthenticationCredentialsNotFoundException(msg);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public TableResult<UserRecord> getUsers(@RequestBody TableParams params) {
        final BeanTableResult<User> beanTableResult = userService.getList(params.getStartIndex(), params.count, params.orderBy);
        final Collection<User> users = beanTableResult.getRows();
        final int usersCount = beanTableResult.getCount();

        final List<UserRecord> result = users.stream().
                map(UserRecord::build).
                collect(Collectors.toList());

        return TableResult.create(result.toArray(new UserRecord[result.size()]), usersCount);
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public ResultRecord updateUser(@RequestBody UserRecord record) {
        logger.info("User: name = " + record.fullName + ", login = " + record.login + ", roles = " + record.roles +
                ", psw = " + record.password + ", id = " + record.id + ", locale: " + record.locale);
/*
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        final User user;
        if (record.id == null) {
            final User existsLoginUser = userService.loadUserByLogin(record.login);
            if (existsLoginUser != null) {
                return ResultRecord.failLocalized("user.login.already.exists", record.login);
            }
            user = new User();
        } else {
            final User existsUser = userService.find(record.id);
            if (existsUser == null) {
                return ResultRecord.fail("Unable find user with id = " + record.id);
            }
            final User existsLoginUser = userService.loadUserByLogin(record.login);
            if (existsLoginUser != null && !existsLoginUser.getId().equals(existsUser.getId())) {
                return ResultRecord.failLocalized("user.login.already.exists", record.login);
            }
            user = existsUser;
        }
        user.setLogin(record.login);
        user.setFullName(record.fullName);
        user.getRoles().clear();
        try {
            user.setRoles(record.roles.stream().
                    map(roleName -> {
                        final Role role = userService.getRole(roleName);
                        if (role == null) {
                            throw new IllegalStateException("Not found role: '" + roleName + "'");
                        }
                        return role;
                    }).
                    collect(Collectors.toSet()));
        } catch (IllegalStateException e) {
            MailSender.sendErrorAsync("Unable set roles", e);
            return ResultRecord.fail(e.getMessage());
        }

        if (!MascotUtils.isEmpty(record.password)) {
            user.setPassword(record.password);
        } else if (record.id == null) {
            return ResultRecord.fail("For new user not defined password");
        }

        if (!MascotUtils.isEmpty(record.locale)) {
            user.setLocale(record.locale.asLocale());
        }

        userService.update(user);

        return ResultRecord.success();
    }

    @RequestMapping(value = "/users/delete", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public ResultRecord deleteUser(@RequestBody Long[] ids) {
/*
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        if (ids == null) {
            return ResultRecord.success();
        }
        logger.info("Delete users size: " + ids.length);
        Stream.of(ids).forEach(id -> {
                    logger.info("Delete user: " + id);
                    try {
                        if (!userService.remove(id)) {
                            logger.warn("Unable delete user: " + id);
                        }
                    } catch (Exception e) {
                        ErrorLogger.error(logger, "Unable delete user: " + id, e);;
                    }
                }
        );
        return ResultRecord.success();
    }

    @RequestMapping(value = "/users/{userName}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    @ResponseBody
    public UserRecord getUser(@PathVariable("userName") String userName) {
        logger.info("User: '" + userService.getCurrentUser().getFullName() + "'");
        logger.info("User id: '" + userService.getCurrentUserId()+ "'");
        return UserRecord.build(userService.loadUserByLogin(userName));
    }

    @RequestMapping(value = "/users/locales", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public LocaleRecord[] getLocales() {
        logger.info("Get locales");
        final List<LocaleRecord> result = Stream.of(new Locale("ru", "RU"), new Locale("en", "UK")).
                map(LocaleRecord::new).
                collect(Collectors.toList());
        return result.toArray(new LocaleRecord[result.size()]);

/*
        final List<String> locales = Stream.of(Locale.getAvailableLocales()).
                filter(locale -> Arrays.binarySearch(allowed, locale.getLanguage()) >= 0).
                map(locale -> locale.toString()).
                collect(Collectors.toList());
        final List<Locale> locales2 = Stream.of(Locale.getAvailableLocales()).
                filter(locale -> Arrays.binarySearch(allowed, locale.getLanguage()) >= 0).
                collect(Collectors.toList());
*/
    }

    @RequestMapping(value = "/users/id/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    @ResponseBody
    public UserRecord getUser(@PathVariable("id") Long userId) {
        logger.info("User: '" + userService.getCurrentUser().getFullName() + "'");
        logger.info("User id: '" + userService.getCurrentUserId()+ "'");
        final User user = userService.find(userId);
        if (user == null) {
            logger.warn("User with id = '" + userId + "' not found");
            throw new IllegalStateException("Unable find user. May be it was deleted");
        }
        return UserRecord.build(user);
    }


    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        return new ResponseEntity<WebError>(new WebError(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    public static void main(String[] args) {
        Map<String, String> orderBy = new HashMap<>();
        orderBy.put("a", "1");
        orderBy.put("b", "2");
        orderBy.put("c", "3");
        final String reduce = orderBy.entrySet().
                stream().reduce(new StringJoiner(", ", " order by ", ""),
                (x, y) -> x.add(y.getKey() + " " + y.getValue()),
                (x, y) -> x.merge(y)).toString();
        System.out.println("reduce = " + reduce);

        final String collect = orderBy.entrySet().stream().collect(Collector.of(
                () -> new StringJoiner(", ", " order by ", ""),
                (x, y) -> x.add(y.getKey() + " " + y.getValue()),
                (x, y) -> x.merge(y),
                StringJoiner::toString
        ));
        System.out.println("collect = " + collect);

    }

}


