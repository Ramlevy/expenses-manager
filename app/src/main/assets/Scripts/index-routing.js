/*starts the main page */
(function(){
   $(window).bind('load', function() {
         indexRouting.mainPage();
         $('#popup-msg').enhanceWithin().popup();
});
})();

 var indexRouting =
    {

    };
/*opening setting activity */
    indexRouting.openSettings = function() {
        var value = window.wb.openSettingsActivity();
        if(tools.statusFromJava(value) == -1){
            tools.popUpErrMsgOnScreen('popup-msg',tools.msgFromJava(value));
        }
    }
/*init the main page*/
    indexRouting.mainPage = function(){
        var todayDate = document.getElementById('today-date');
        if(todayDate != null)
        {
            var d = new Date();

            var month = d.getMonth()+1;
            var day = d.getDate();

            var output =(day<10 ? '0' : '') + day + '/' +
            (month<10 ? '0' : '') + month + '/' +
            d.getFullYear()

             $('#today-date').empty();
            var text = document.createTextNode(output);
             todayDate.appendChild(text);

             var balance = document.getElementById('total-balance');
              $('#total-balance').empty();
             var a = document.createElement('a');
             a.setAttribute('href','');
             a.setAttribute('onclick','indexRouting.switchPage(2,1)');
             var value = window.wb.getAllExpenseItemsPrice();
             if(tools.statusFromJava(value) == -1){
                tools.popUpErrMsgOnScreen('popup-msg',tools.msgFromJava(value));
             }
             else{
                text = document.createTextNode('Balance : ' + tools.msgFromJava(value) + ' NIS');
                a.appendChild(text);
                balance.appendChild(a);
             }
        }
    }
/*init the main page Filter Form*/
     indexRouting.initFilterForm = function () {
            dbhandle.initSelectCategory('filter-categories', 'All');
            $('#filtered-categories').empty();
            document.getElementById('filter-date-from').value = '';
            document.getElementById('filter-date-to').value = '';
            $('#chart-container1').empty();
            $('#chart-container2').empty();
     }
/*helps for the back option*/
     indexRouting.pagesNumberStack = [];
/*switch between pages*/
     indexRouting.switchPage = function(switchToPageNumber, currentPageNumber, expenseItemId) {
                     if( currentPageNumber > 0)
                         indexRouting.pagesNumberStack.push(currentPageNumber);
                     switch(switchToPageNumber)
                     {
                         case 1:
                         {
                             indexRouting.mainPage();
                             $.mobile.navigate('#home-page');
                             break;
                         }
                         case 2:
                         {
                             dbhandle.displayCategoriesWithExpenseItems();
                             $.mobile.navigate('#balance-page');
                             break;
                         }
                         case 3:
                         {
                             dbhandle.initExpenseItemForm(expenseItemId)
                             $.mobile.navigate('#expense-item-page');
                             break;
                         }
                         case 4:
                         {
                             indexRouting.initFilterForm();
                             $.mobile.navigate('#filter-page');
                             break;
                         }
                         case 5:
                         {
                             $.mobile.navigate('#about-page');
                         }
                     }
     }
/*when back is pressed switch between pages*/
     indexRouting.back = function() {
                    try{
                         if(indexRouting.pagesNumberStack.length > 0 ) {
                             var lastPageNumber = indexRouting.pagesNumberStack.pop();
                             indexRouting.switchPage(lastPageNumber, 0);
                         }
                         else {
                             var value = window.wb.exitActivity();
                            if(tools.statusFromJava(value) == -1){
                                  throw new tools.Exception(tools.msgFromJava(value));
                            }
                          }
                    }catch(ex){
                          tools.popUpErrMsgOnScreen('popup-msg',ex.getMessage());
                    }
     }