(function () {
    'use strict';

    angular.module('localization')
        .value('localizedTextsEN', {
            '': '+ Kategorie',
            'edit Carte': 'Karte bearbeiten',

            /*common*/
            'common.new' : 'New',
            'common.edit' : 'Edit',
            'common.remove' : 'Remove',
            'common.save' : 'Save',
            'common.cancel' : 'Cancel',
            'common.close' : 'Close',
            'common.clear' : 'Clear',
            'common.error' : 'Danger',
            'common.loading' : 'Loading',
            'common.no.result.found' : 'No Results Found',
            'common.yes' : 'Yes',
            'common.no' : 'No',
            'common.warning' : 'Warning',
            'common.filter.hide' : 'Hide filter',
            'common.filter.show' : 'Show filter',
            'common.filter.clear.tooltip' : 'Clear filter',
            'common.document.title' : 'Customer portal',
            'typeahead.fixed.element.hint' : 'Use last selected element',

            'calendar.current.day' : 'Today',

            /*top.menu*/
            'top.menu.logout' : 'Logout',
            'top.menu.orders' : 'Orders',
            'top.menu.new_order' : 'New order',
            'top.menu.new_product' : 'New furniture order',
            'top.menu.new_cloth' : 'New cloth order',
            'top.menu.app.name' : 'Customer portal',
            'top.menu.home' : 'Home',

            /*top.menu.user.details*/
            'top.menu.user.details.name' : 'User name',
            'top.menu.user.details.roles' : 'Roles',

            /*order.product.table*/
            'order.product.table.column.product' : 'Furniture',
            'order.product.table.column.cloth-main' : 'Main cloth',
            'order.product.table.column.cloth-comp-1' : 'Cloth comp 1',
            'order.product.table.column.cloth-comp-2' : 'Cloth comp 2',
            'order.product.table.column.stitching.type' : 'Stitching',
            'order.product.table.column.count' : 'Count',
            'order.product.table.column.cost' : 'Cost',
            'order.product.table.remove.button' : 'Remove from order',
            'order.product.table.append.button' : 'Append',
            'order.product.table.edit.title.new' : 'Append product to order',
            'order.product.table.edit.title.edit' : 'Edit order product',
            'order.product.table.edit.product' : 'Product',
            'order.product.table.edit.product.required' : 'Product required',
            'order.product.table.edit.main.cloth' : 'Main cloth',
            'order.product.table.edit.main.cloth.required' : 'Main cloth required',
            'order.product.table.edit.comp.cloth.1' : 'Cloth comp 1',
            'order.product.table.edit.comp.cloth.1.required' : 'Cloth comp 1 required',
            'order.product.table.edit.comp.cloth.2' : 'Cloth comp 2',
            'order.product.table.edit.comp.cloth.2.required' : 'Cloth comp 2 required',
            'order.product.table.edit.count' : 'Count',
            'order.product.table.edit.count.hint' : 'Product count',
            'order.product.table.edit.count.required' : 'Product count required',
            'order.product.table.edit.stitching' : 'Stitching',

            /*order.cloth.table*/
            'order.cloth.table.column.cloth.name' : 'Cloth',
            'order.cloth.table.column.count' : 'Count',
            'order.cloth.table.column.cost' : 'Cost',
            'order.cloth.table.edit.cloth' : 'Cloth',
            'order.cloth.table.edit.cloth.required' : 'Cloth required',
            'order.cloth.table.edit.count' : 'Count',
            'order.cloth.table.edit.count.hint' : 'Cloth count',
            'order.cloth.table.edit.count.required' : 'Count required',
            'order.cloth.table.edit.title.new' : 'Append cloth to order',
            'order.cloth.table.edit.title.edit' : 'Change cloth in order',
            'order.cloth.table.append.button' : 'Append cloth',
            'order.cloth.table.remove.button' : 'Remove from order',

            /*orders.table*/
            'stitching.STANDARD' : 'Standard',
            'stitching.LIGHT' : 'Light',
            'stitching.DARK' : 'Dark',
            'order.table.save-order-only' : 'Save wo send',
            'order.table.save-order-only.hint' : 'Save order but not send',
            'order.table.save-order.and.send' : 'Save and send',
            'order.table.save-order.and.send.hint' : 'Save order and send',
            'order.table.save.order' : 'Save',
            'order.table.order.top.title.exists' : 'Order №',
            'order.table.order.top.title.new' : 'New order',
            'order.table.order.status.sent' : 'sent',
            'order.table.order.status.not.sent' : 'not send',
            'order.table.order.top.title.status' : 'Status:',
            'order.type.PRODUCT' : 'Product',
            'stitching.CLOTH' : 'Cloth',
            'orders.table.column.id' : '№',
            'orders.table.column.creation.date' : 'Creation date',
            'orders.table.column.modify.date' : 'Modify date',
            'orders.table.column.cost' : 'Cost',
            'orders.table.column.status' : 'Status',
            'orders.table.column.type' : 'Order type',
        });

})();