(function () {
    'use strict';

    angular.module('localization')
        .value('localizedTextsRU', {
            '+ Category': '+ Категория',
            'edit Carte': 'Карта',

            /*common*/
            'common.new' : 'Создать',
            'common.edit' : 'Редактировать',
            'common.remove' : 'Удалить',
            'common.save' : 'Сохранить',
            'common.cancel' : 'Отмена',
            'common.close' : 'Закрыть',
            'common.clear' : 'Очистить',
            'common.error' : 'Ошибка',
            'common.loading' : 'Загрузка ...',
            'common.no.result.found' : 'Ничего не найдено',
            'common.yes' : 'Да',
            'common.no' : 'Нет',
            'common.warning' : 'Предупреждение',
            'common.filter.hide' : 'Скрыть фильтры',
            'common.filter.show' : 'Показать фильтр',
            'common.filter.clear.tooltip' : 'Очистить фильтр',
            'common.document.title' : 'Система недельного расчета стоимости выполненных работ',
            'typeahead.fixed.element.hint' : 'Последний выбранный элемент',

            'calendar.current.day' : 'Сегодня',

            /*top.menu*/
            'top.menu.logout' : 'Выход',
            'top.menu.orders' : 'Список заказов',
            'top.menu.new_order' : 'Новая заявка',
            'top.menu.new_product' : 'Новaя заявка на мебель',
            'top.menu.new_cloth' : 'Новая заявка на ткани',
            'top.menu.app.name' : 'Портал приема заказов',
            'top.menu.home' : 'Главная',

            /*top.menu.user.details*/
            'top.menu.user.details.name' : 'Пользователь',
            'top.menu.user.details.roles' : 'Роли'


        });

})();