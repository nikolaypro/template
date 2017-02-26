(function () {
    'use strict';

    angular.module('localization')
        .value('localizedTextsRU', {
            '+ Category': '+ Категория',
            'edit Carte': 'Карта',

            /*common*/
            'common.new' : 'Создать',
            'common.edit' : 'Изменить',
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
            'common.document.title' : 'Портал приема заказов',
            'typeahead.fixed.element.hint' : 'Последний выбранный элемент',

            'calendar.current.day' : 'Сегодня',

            /*top.menu*/
            'top.menu.logout' : 'Выход',
            'top.menu.orders' : 'Список заказов',
            'top.menu.new_order' : 'Новая заявка',
            'top.menu.new_product' : 'Новaя заявка на мебель',
            'top.menu.new_cloth' : 'Новая заявка на ткань',
            'top.menu.app.name' : 'Портал приема заказов',
            'top.menu.home' : 'Главная',

            /*top.menu.user.details*/
            'top.menu.user.details.name' : 'Пользователь',
            'top.menu.user.details.roles' : 'Роли',

            /*order.product.table*/
            'order.product.table.column.product' : 'Товар',
            'order.product.table.column.cloth-main' : 'Ткань основная',
            'order.product.table.column.cloth-comp-1' : 'Ткань комп 1',
            'order.product.table.column.cloth-comp-2' : 'Ткань комп 2',
            'order.product.table.column.stitching.type' : 'Строчка',
            'order.product.table.column.count' : 'Кол-во',
            'order.product.table.column.cost' : 'Цена',
            'order.product.table.remove.button' : 'Убрать товар из заявки',
            'order.product.table.append.button' : 'Добавить товар',
            'order.product.table.edit.title.new' : 'Добавление товара в заявку',
            'order.product.table.edit.title.edit' : 'Изменение товара в заявке',
            'order.product.table.edit.product' : 'Товар',
            'order.product.table.edit.product.required' : 'Необходимо указать товар',
            'order.product.table.edit.main.cloth' : 'Основная ткань',
            'order.product.table.edit.main.cloth.required' : 'Необходимо указать основную ткань',
            'order.product.table.edit.comp.cloth.1' : 'Ткань комп 1',
            'order.product.table.edit.comp.cloth.1.required' : 'Требуется указать ткань комп 1',
            'order.product.table.edit.comp.cloth.2' : 'Ткань комп 2',
            'order.product.table.edit.comp.cloth.2.required' : 'Требуется указать ткань комп 2',
            'order.product.table.edit.count' : 'Количество',
            'order.product.table.edit.count.hint' : 'Количество товара',
            'order.product.table.edit.count.required' : 'Необходимо указать количество товара',
            'order.product.table.edit.stitching' : 'Строчка',
            'stitching.STANDARD' : 'Стандарт',
            'stitching.LIGHT' : 'Светлая',
            'stitching.DARK' : 'Темная',
            'order.table.save-order-only' : 'Записать без отправки',
            'order.table.save-order-only.hint' : 'Записать заявку с целью дальнейшего изменения, но не отправлять',
            'order.table.save-order.and.send' : 'Записать и отправить',
            'order.table.save-order.and.send.hint' : 'Записать заявку и отправить',
            'order.table.save.order' : 'Сохранить',
            'order.table.order.top.title.exists' : 'Заявка №',
            'order.table.order.top.title.new' : 'Новая заявка',
            'order.table.order.status.sent' : 'отправлена',
            'order.table.order.status.not.sent' : 'не отправлена',
            'order.table.order.top.title.status' : 'Статус:',

            /*orders.table*/
            'order.type.PRODUCT' : 'Мебель',
            'order.type.CLOTH' : 'Ткань',
            'orders.table.column.id' : '№',
            'orders.table.column.creation.date' : 'Дата создания',
            'orders.table.column.modify.date' : 'Дата изменения',
            'orders.table.column.cost' : 'Сумма',
            'orders.table.column.status' : 'Статус',
            'orders.table.column.type' : 'Тип заказа',
            'orders.table.order.view' : 'Просмотр',
            'orders.table.order.edit' : 'Редактировать',
        });

})();