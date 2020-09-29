/*when first Entrance to setting page*/
(function(){
   $(window).bind('load', function() {
            settings.settingsCategorySeletion();
        });
   $(document).ready(function(){
        $('#settings-category').change( function() {
               var $this = $(this), val = $this.val();
               settings.firstEntrance(val);
        });
    });$
})
();
var settings = {}

/*Setts the radio buttons*/
settings.firstEntrance = function(categoryId){
      var category = window.swb.getCategoryById(parseInt(categoryId));
      if(tools.statusFromJava(category) == -1){
           tools.popUpErrMsgOnScreen('popup-msg',tools.msgFromJava(category));
      }
      else
      {
           var categoryJson = JSON.parse(tools.msgFromJava(category));
           document.getElementById('expenses-limit').value = categoryJson.expensesLimit;
           $('input:radio[name="expense-period"]').filter('[value = ' + categoryJson.expensePeriod + ']').prop('checked',true);
           $('input:radio[name="expense-period"]').checkboxradio('refresh');
           document.getElementById('start-expenses-date').value =   tools.shownDateFormat(categoryJson.startExpensesDate);
      }
}

/*Setts the selection in the settings page*/
settings.settingsCategorySeletion = function() {
        dbhandle.initSelectCategory('settings-category');
        settings.firstEntrance(document.getElementById('settings-category').selectedOptions[0].value);
}

/*updates category*/
settings.updateCategory = function() {
      var category = { }
      category.expensesLimit = document.getElementById('expenses-limit').value;
      category.id = document.getElementById('settings-category').selectedOptions[0].value;
      category.expensePeriod = $('input[name="expense-period"]:checked').val();
      category.startExpensesDate = document.getElementById('start-expenses-date').value;
      var value = window.swb.updateCategory(JSON.stringify(category));
      if(tools.statusFromJava(value) == -1){
         tools.popUpErrMsgOnScreen('popup-msg',tools.msgFromJava(value));
      }
      else{
         tools.popUpMsgOnScreen('popup-msg','Category settings Saved!');
      }
}



