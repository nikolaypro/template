package com.mascot.service.controller.user;

import com.mascot.server.model.User;
import com.mascot.service.controller.common.AutocompleteType;

/**
 * Created by Nikolay on 24.11.2016.
 */
public class LoginUserRecord extends UserRecord {
    public String appVersion;
    public AutocompleteType productAutocompleteType;
    public Boolean reportGroupEnabled;
    public Boolean salaryReportWithSubTypesEnabled;

    public static LoginUserRecord build(User user, String appVersion, AutocompleteType productAutocompleteType,
                                        Boolean reportGroupEnabled, Boolean salaryReportWithSubTypesEnabled) {
        if (user == null) {
            return null;
        }

        final UserRecord userRecord = UserRecord.build(user);

        LoginUserRecord result = new LoginUserRecord();
        result.id = userRecord.id;
        result.fullName = userRecord.fullName;
        result.login = userRecord.login;
        result.roles = userRecord.roles;
        result.locale = userRecord.locale;
        result.appVersion = appVersion;
        result.productAutocompleteType = productAutocompleteType;
        result.reportGroupEnabled = reportGroupEnabled;
        result.salaryReportWithSubTypesEnabled = salaryReportWithSubTypesEnabled;

        return result;
    }

}
