var dbhandle =
    {
    /*Remembering the expenseItem id when pressed*/
        selectedExpenseItemID : null
    };
    /*Displaying ExpenseItems Between Dates By Category*/
    dbhandle.displayExpenseItemsBetweenDatesByCategory = function(){
    var from = document.getElementById('filter-date-from').value;
    var to = document.getElementById('filter-date-to').value;
    var categoryId = document.getElementById('filter-categories').selectedOptions[0].value;;
    var expenseItemsStr;
    var value;
    var foundProblem = false;
    if(categoryId == 0) {
        value = window.wb.getExpenseItemsBetween(from,to);
        if(tools.statusFromJava(value) == -1){
            tools.popUpErrMsgOnScreen('popup-msg',tools.msgFromJava(value));
            foundProblem = true;
        }else{
            expenseItemsStr = JSON.parse(tools.msgFromJava(value));
            dbhandle.paintGraph(from, to);
        }
        }else{
            value = window.wb.getCategoryExpenseItemsBetween(from,to,categoryId);
            if(tools.statusFromJava(value) == -1){
                tools.popupErrMsgOnScreen('popup-msg',tools.msgFromJava(value));
                foundProblem = true;
            }
            else{
                expenseItemsStr =  JSON.parse(tools.msgFromJava(value));
                dbhandle.paintGraph(from, to, categoryId);
            }
        }
    if(!foundProblem){
        var vecExpenseItems = expenseItemsStr;
        var categoriesList = document.getElementById('filtered-categories');
        $('#filtered-categories').empty();
        var ulExpenseItems = document.createElement('ul');
        ulExpenseItems.setAttribute('data-role','listview');
        ulExpenseItems.setAttribute('data-split-icon','delete');
        ulExpenseItems.setAttribute('data-split-theme','d');
        vecExpenseItems.forEach(
            function(expenseItem){
                var liExpenseItem = tools.printExpenseItem(expenseItem);
                ulExpenseItems.appendChild(liExpenseItem);
                }
            );
        categoriesList.insertBefore(ulExpenseItems, categoriesList.firstChild);
        if ( $('#filtered-categories').hasClass('ui-listview'))
                {
                        $('#filtered-categories').listview('refresh');
                }
                else
                {
                        $('#filtered-categories').trigger('create');
                }
        }
    }
    /*Prints the categories lists*/
    dbhandle.printCategoryList = function(category){
             var ulCategory = document.createElement('ul');
             ulCategory.setAttribute('data-role','listview');
             var liCategory = document.createElement('li');
             liCategory.setAttribute('data-role','collapsible');
             liCategory.setAttribute('data-inset','false');
             liCategory.setAttribute('data-icon','false');
             var text = document.createTextNode(category.name);
             var h2 = document.createElement('h2');
             h2.appendChild(text);
             var p = document.createElement('p');
             p.setAttribute('style','color:blue');
             var value = window.wb.getCategoryPrice(category._id);
             if(tools.statusFromJava(value) == -1){
                        throw new tools.Exception(tools.msgFromJava(value));
             }
             text = document.createTextNode('Price: ' +  tools.msgFromJava(value));
             p.appendChild(text);
             h2.appendChild(p);
             liCategory.appendChild(h2);
             var ulExpenseItems = document.createElement('ul');
             ulExpenseItems.setAttribute('data-role','listview');
             ulExpenseItems.setAttribute('data-split-icon','delete');
             ulExpenseItems.setAttribute('data-split-theme','d');
             var expenseItemsStr = window.wb.getExpenseItemsByCategory(category._id);
             if(tools.statusFromJava(expenseItemsStr) == -1){
                throw new tools.Exception(tools.msgFromJava(expenseItemsStr));
             }
             var vecExpenseItems =  JSON.parse(tools.msgFromJava(expenseItemsStr));
             vecExpenseItems.forEach(
                 function(expenseItem){
                         var liExpenseItem = tools.printExpenseItem(expenseItem);
                         ulExpenseItems.appendChild(liExpenseItem);
                 }
             );
             liCategory.appendChild(ulExpenseItems);
             ulCategory.appendChild(liCategory);
             return ulCategory;
    }

    /*displaying Categories With their ExpenseItems*/
    dbhandle.displayCategoriesWithExpenseItems = function() {
        var categoryStr = window.wb.getCategories();
        if(tools.statusFromJava(categoryStr) == -1){
            tools.popUpErrMsgOnScreen('popup-msg',tools.msgFromJava(categoryStr));
        }
        else{
            var vecCategories = JSON.parse(tools.msgFromJava(categoryStr));
            var categoriesList = document.getElementById('categories');
            $('#categories').empty();
            try{
                vecCategories.forEach(
                function(category)
                {
                    var ulCategory = dbhandle.printCategoryList(category);
                    categoriesList.appendChild(ulCategory);
                }
                );
                if ( $('#categories').hasClass('ui-listview'))
                {
                        $('#categories').listview('refresh');
                }
                else
                {
                        $('#categories').trigger('create');
                }
            }catch(ex){
                tools.popUpErrMsgOnScreen('popup-msg',ex.getMessage());
            }
        }
    }

    /*checking if the price is over the limit of the category in the given dates*/
    dbhandle.isPassedLimit = function(expenseItem)
    {
        var expenseItemValue = parseFloat(expenseItem.expenseValue);
        if(isNaN(expenseItemValue)){
            throw new tools.Exception('Wrong Expense Item Price, please fill the text fields correctly');
        }
        var value = window.wb.isAboveTheCategoryLimits(expenseItem.date, expenseItem.categoryID, expenseItemValue, dbhandle.selectedExpenseItemID)
        if(tools.statusFromJava(value) == -1){
            throw new tools.Exception(tools.msgFromJava(value));
        }
        return tools.msgFromJava(value);
    }

    /*add or updates the expenseItems in the db*/
    dbhandle.addNewOrUpdateExpenseItem = function() {
            var expenseItem = { }
            var value;
            var limitReached = false;
            expenseItem.categoryID = document.getElementById('category').selectedOptions[0].value;
            expenseItem.name = document.getElementById('expense-item-name').value;
            expenseItem.expenseValue = document.getElementById('expense-item-price').value;
            expenseItem.date = document.getElementById('expense-item-datetime').value;
            expenseItem.comment = document.getElementById('expense-item-comment').value;
            try{
               if(dbhandle.isPassedLimit(expenseItem) == 'true') {
                    tools.popUpErrMsgOnScreen('popup-msg','You have reached your limit expense. Item saved!');
                    limitReached = true;
               }
               if(dbhandle.selectedExpenseItemID == null)
                {
                  expenseItem.id = -1;
                  value = window.wb.addExpenseItem(JSON.stringify(expenseItem));
                  if(tools.statusFromJava(value) == -1){
                       throw new tools.Exception(tools.msgFromJava(value));
                  } else if(!limitReached) {
                    tools.popUpMsgOnScreen('popup-msg','Item Saved!');
                  }
                }
                else
                {
                  expenseItem.id = dbhandle.selectedExpenseItemID;
                  value = window.wb.updateExpenseItem(JSON.stringify(expenseItem));
                  if(tools.statusFromJava(value) == -1){
                       throw new tools.Exception(tools.msgFromJava(value));
                  } else if(!limitReached) {
                    tools.popUpMsgOnScreen('popup-msg','Item Saved!');
                  }
               }

            }catch(ex){
                 tools.popUpErrMsgOnScreen('popup-msg',ex.getMessage());
            }

        }


    /*displaying Categories in the select ,option*/
    dbhandle.initSelectCategory = function(selectName, defaultSelectOption) {
         var categoryStr = window.wb.getCategories();
         if(tools.statusFromJava(categoryStr) == -1){
            tools.popUpErrMsgOnScreen('popup-msg',tools.msgFromJava(categoryStr));
         }
         var vecCategories =  JSON.parse(tools.msgFromJava(categoryStr));
         var categoriesList = document.getElementById(selectName);
         $('#' + selectName).empty();
         if(defaultSelectOption) {
            var optionCategory =  tools.printCategorySelection(null, 0, defaultSelectOption);
            categoriesList.appendChild(optionCategory);
         }
          vecCategories.forEach(
                function(category)
                {
                        var optionCategory =  tools.printCategorySelection(category);
                        categoriesList.appendChild(optionCategory);
                });
    }


    /*init expenseItem form if expenseItem is pressed then the values of the text fields will be fill*/
    dbhandle.initExpenseItemForm = function(expenseItemId) {
         document.getElementById('my-form').reset();
         dbhandle.initSelectCategory('category');
         try{
            if(expenseItemId != undefined)
            {
                  dbhandle.selectedExpenseItemID = expenseItemId;
                  var onClickExpenseItemObj = window.wb.getExpenseItemByID(expenseItemId);
                  if(tools.statusFromJava(onClickExpenseItemObj) == -1){
                    throw new tools.Exception(tools.msgFromJava(onClickExpenseItemObj));
                  }
                  var expenseItemJson =  JSON.parse(tools.msgFromJava(onClickExpenseItemObj));
                  var categoriesList = document.getElementById('category');
                  var matchesOptionValueIndex = 0;
                  for (var i=0; i<categoriesList.options.length; i++){
                    if (categoriesList.options[i].value == expenseItemJson.categoryId){
                      matchesOptionValueIndex = i;
                      break;
                    }
                 }
                 categoriesList.selectedIndex = matchesOptionValueIndex;
                 document.getElementById('expense-item-name').value = expenseItemJson.name;
                 document.getElementById('expense-item-price').value = expenseItemJson.value;
                 document.getElementById('expense-item-datetime').value = tools.shownDateFormat(expenseItemJson.actionDate);
                 document.getElementById('expense-item-comment').value = expenseItemJson.comment;
            }
            else
            {
              dbhandle.selectedExpenseItemID = null;
            }
         }catch(ex){
             tools.popUpErrMsgOnScreen('popup-msg',ex.getMessage());
         }
    }

    /*deleting expenseItem from the db*/
    dbhandle.deleteExpenseItem = function(expenseItemId) {
          var value = window.wb.deleteExpenseItem(expenseItemId);
          if(tools.statusFromJava(value) == -1){
              tools.popUpErrMsgOnScreen('popup-msg',tools.msgFromJava(value));
          }
          var hrefOfUrl = window.location.href;
          var pageName = hrefOfUrl.substring(hrefOfUrl.indexOf('#'));
          if(pageName == '#balance-page'){
              dbhandle.displayCategoriesWithExpenseItems();
          }else if(pageName == '#filter-page'){
              dbhandle.displayExpenseItemsBetweenDatesByCategory();
          }
    }

    /*painting the graphs*/
    dbhandle.paintGraph = function(from, to, categoryId){
        var vecGraphData = [];
        if(categoryId && categoryId != undefined)
        {
               var expenseItemsJson =  JSON.parse(tools.msgFromJava(window.wb.getCategoryExpenseItemsBetween(from, to, categoryId)));
               var vecExpenseItemsInfo = [];
               expenseItemsJson.forEach(
               function(expenseItemJson)
               {
                    var expenseItemPrice = parseFloat(expenseItemJson.value)
                    vecGraphData.push( { label: expenseItemJson.name, y: expenseItemPrice });
               });
        }
        else
        {
              var categoryStr = window.wb.getCategories();
              var vecCategories =  JSON.parse(tools.msgFromJava(categoryStr));

               vecCategories.forEach(
                              function(category)
                              {
                var sum = parseFloat(tools.msgFromJava(window.wb.getCategoryPriceBetweenDates(from, to, category._id)));
                vecGraphData.push( { label: category.name, y: sum });
                });

        }
        $('#chart-container1').empty();
        $('#chart-container2').empty();
        var chartLine = new CanvasJS.Chart('chart-container1', {
            data: [
            {
              type: 'line',
                dataPoints: vecGraphData
            }]
        });
        chartLine.render();
        var chartPie = new CanvasJS.Chart('chart-container2', {
                          data: [
                          {
                            type: 'pie',
                            dataPoints: vecGraphData
                          }]
                      });
        chartPie.render();
     }