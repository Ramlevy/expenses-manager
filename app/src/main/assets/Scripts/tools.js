var tools = {};

/*C'tor of exception*/
tools.Exception = function(msg){
    tools.Exception.prototype.setMessage(msg);
}

/*sets message*/
tools.Exception.prototype.getMessage = function(){
    return this.message;
}

/*gets message*/
tools.Exception.prototype.setMessage = function(msg){
       this.message = msg;
}

/*create the Title For PopUpMsg*/
tools.createTitleForPopUpMsg = function(title) {
     var h1 = document.createElement('h1');
     h1.setAttribute('style','color:red');
     text = document.createTextNode(title);
     h1.appendChild(text)
     return h1;
}

/*create the Content For PopUpMsg*/
tools.createContentForPopUpMsg = function(msg,color) {
     var  p = document.createElement('p');
     p.setAttribute('style','color:' + color);
     text = document.createTextNode(msg);
     p.appendChild(text);
     return p;
}

/*create the PopUpMsg*/
tools.createPopUpMsg = function(popUpName) {
    var popUp = document.getElementById(popUpName);
    $('#'+popUpName).empty();
    var a = document.createElement('a');
    a.setAttribute('class','ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-notext ui-btn-right');
    a.setAttribute('data-rel','back');
    a.setAttribute('href','#');
    var text = document.createTextNode('Close');
    a.appendChild(text);
    popUp.appendChild(a);
    return popUp;
}

/*displays the error PopUpMsg*/
tools.popUpErrMsgOnScreen = function(popUpName,msg){
     var popUp = tools.createPopUpMsg(popUpName);
     var h1 = tools.createTitleForPopUpMsg('Warning !!!')
     var  p = tools.createContentForPopUpMsg(msg,'#b1202a');
     popUp.appendChild(h1);
     popUp.appendChild(p);
     $('#'+popUpName).popup('open');
}

/*displays the PopUpMsg*/
tools.popUpMsgOnScreen = function(popUpName,msg){
     var popUp = tools.createPopUpMsg(popUpName);
     var  p = tools.createContentForPopUpMsg(msg,'green');
     popUp.appendChild(p);
     $('#'+popUpName).popup('open');
}

/*Returns the message from java*/
tools.msgFromJava = function(fromJava){
    var fromJavaToJson = JSON.parse(fromJava);
    var message = fromJavaToJson.message;
    return message;
}

/*Returns the status from java if 1 or -1 and then the message will be error message*/
tools.statusFromJava = function(fromJava){
    var fromJavaToJson = JSON.parse(fromJava);
    var status = fromJavaToJson.status;
    return status;
}

/*setting the date for display*/
tools.shownDateFormat = function(dateAndTime){
      var date = new Date(dateAndTime);
      var year = date.getFullYear();
      var month = ((date.getMonth() + 1) < 10 ? ('0' + (date.getMonth() + 1)) : (date.getMonth() + 1));
      var day   = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate());
      return (year + '-' + month + '-' + day);
}

/*Prints expenseItems in list*/
 tools.printExpenseItem = function(expenseItem){
      var liExpenseItem = document.createElement('li');
      liExpenseItem.setAttribute('data-icon','false');
      text = document.createTextNode(expenseItem.name);
      var a = document.createElement('a');
      a.setAttribute('class','ui-link-inherit');
      var h3 = document.createElement('h3');
      h3.appendChild(text);
      text = document.createTextNode('Price: ' + expenseItem.value);
      p = document.createElement('p');
      p.setAttribute('style','color:blue');
      p.appendChild(text);
      a.appendChild(h3);
      a.appendChild(p);
      text = document.createTextNode('Date: ' + tools.shownDateFormat(expenseItem.actionDate));
      p = document.createElement('p');
      p.setAttribute('style','color:green');
      p.appendChild(text);
      a.appendChild(p);
      text = document.createTextNode(expenseItem.comment);
      p = document.createElement('p');
      p.setAttribute('style','color:black');
      p.appendChild(text);
      a.appendChild(p);
      a.setAttribute('href','');
      a.setAttribute('onclick','indexRouting.switchPage(3,2,'+expenseItem._id+')');
      var aDelete = document.createElement('a');
      aDelete.setAttribute('class','delete')
      aDelete.setAttribute('onclick','dbhandle.deleteExpenseItem('+expenseItem._id+')');
      aDelete.setAttribute('href','');
      liExpenseItem.appendChild(a);
      liExpenseItem.appendChild(aDelete);
      return liExpenseItem;
}
/*Prints category in select,option*/
 tools.printCategorySelection = function(category, value, name){
      if(category && category != null) {
          value = category._id;
          name = category.name;
      }
      var optionCategory = document.createElement('option');
      optionCategory.setAttribute('value', value);
      var text = document.createTextNode(name);
      optionCategory.appendChild(text);
      return optionCategory;
}


