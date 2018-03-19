/**
  使用说明：
  在使用页的<head>部分加上：
  <script language="javascript" src="g_js.js"></script>
  在要应用的表单元素上设置本程序支持的几种属性，如<input type="text" title="这是说明文字">

  具体说明：
  对于以下示例，文本框代表以下三种类型：<input type="text"> <input type="password"> <textarea></textarea>
  表单元素代表<input type=...> <select>

  功能1：
  设置表单元素的title属性（一般为此元素的说明文字），当元素得到焦点时在右侧显示该文字，失去焦点时隐藏。
  示例：
  帐号：<input type="text" title="登录时的ID号">

  功能2：
  设置文本框的maxlen属性（限制文本框内容的字节长度，-- 中文字符按2个字节，英文字符按1个字节计算），当用户输入的内容超长时，自动截取。
  文本框下面有红线显示已输入的内容占可输入最大长度的百分比。
  示例：
  <input type="text" maxlen="6">

  功能3:
  设置文本框的regExp属性（文本框要符合的正则表达式）和regExpErrorInfo（内容不符合正则表达式时的提示信息）。当包含该文本框的表单提交时(onsubmit)，
  开始验证文本框的内容有效性，如果不符合，则取消提交，聚焦到该文本框并提示相关信息。
  也可以设置allowEmpty属性（是否允许为空。当值为true时如果内容为空则直接通过验证，当值为false时如果内容为空则直接不通过验证，
  如果不设置则继续验证其它的条件如正则表达式来确定是否通过验证）。
  当设置了allowEmpty属性值为"false"且当前内容为空时，验证不通过。
  此时如果设置了emptyErrorInfo，则提示该信息。
  示例：
  <form>
  数量：<input type="text" regExp="[1-9][0-9]{0,3}" regExpErrorInfo="必须为1到9999之间的数字"><br>
  姓名：<input type="text" allowEmpty="false" emptyErroInfo="姓名不能为空"><br>
  <input type="submit">
  </form>


*/

/**
  配置数据
*/

//设为true才会支持自定义的title属性特性
window.showTitleEnabled = true;

//设为true才会支持自定义的regExp/regExpErrorInfo/allowEmpty/emptyErrorInfo属性特性
window.validateInputEnabled = true;
  //设为true才会在文本框下显示红线表可用内容长度
  window.validateInputLenShowProgressEnabled = false;

//设为true才会支持自定义的maxlen属性
window.limitInputLenEnabled = true;


//*********************************************************************************************************************
//**********************************----------------------------------------------------------------------------------
//以下是代码正文，使用者不用关心。
//**********************************----------------------------------------------------------------------------------
//***********************************************************************************************************************



/**
* 去掉字符串的首尾空格或tab字符
* @return 去掉首尾空格的字符串
* 用法 " abc ".trim()  -- 返回值为"abc"
*/
String.prototype.trim = function()
{
  return this.replace(/(^\s+)|(\s+$)/g,"");
}

/**
* 取得一个字符串的字节长度（按中文字符占2个字节，英文1个字节计算）
* @return 该字符串的字节长度
* 用法 "abc中国".len() -- 返回值为7
*/
String.prototype.len = function()
{
  return this.replace(/[\u00FF-\uFFFF]/g,"aa").length;
}

/**
* 返回一个字符串前面指定字节数的子字符串（按中文字符占2个字节，英文1个字节计算）
* @return 截到的子字符串
* 用法 "abc中国".head(4) -- 返回"abc"
*      "abc中国".head(5) -- 返回"abc中"
*/
String.prototype.head = function(maxlen)
{
  var result = this;
  for(var i=this.length-1;i>=0;i--)
  {
    result = this.substring(0,i);
	if(result.len()<=maxlen)
	{
	  return result;
	}
  }
  return result;
}


/**
以下代码功能：
    文本框按字节数限长，英文字符按1个字节，中文按2个字节计算。

用法：在页面的head部分，加上
    <script language="javascript" src="g_js.js"></script>

	给要设置的文本框设置maxlen属性，属性值为最大的字节数，例：
<input type=text maxlen="6">
<input type=password maxlen="7">
<textarea maxlen="6"></textarea>

*/

/**得到一个元素的绝对坐标和长宽值,单位象素
*@return 一个结构体
					left	绝对坐标的x值
					top	绝对坐标的y值
					width	元素的宽度
					heigth	元素的长度
*/
function getAbsolutePos(obj)
{
  var left = obj.offsetLeft;
  var top = obj.offsetTop;
  var ele = obj;
  while((ele=ele.offsetParent)!=null)
  {
	left += ele.offsetLeft;
	top += ele.offsetTop;
  }

  return {
	  left:left,
	  top:top,
	  width:obj.offsetWidth,
	  height:obj.offsetHeight
	};
}

/**
* 将一个元素的value值截掉指定长度后面部分(英文字符按1，中文字符按2计算)
* @param theUniqueID 元素的uniqueID
* @param maxlen 指定的最大长度
*/
function trimValue(theUniqueID,maxlen)
{
  var obj = document.getElementById(theUniqueID);
  if(typeof(obj)=="undefined")return;
  if(obj.value.len()<=maxlen)return;
  var str = obj.value.head(maxlen);
  obj.value = str;

}

/**
* 文本框的onpropertychange事件处理器
*/
function text_propertychange()
{
    if(event.propertyName!="value")return;
       var input = event.srcElement;
	var maxlen = input.getAttribute("maxlen");
	if(maxlen==null)return;
	maxlen = parseInt(maxlen);
	if(isNaN(maxlen))return;

	if(input.value.len()>maxlen)
	{
	  //alert("不能这么长！！！" );
	  //input.value = input.value.head(maxlen);
	  input.blur();
	  input.focus();
	  setTimeout("trimValue('"+input.uniqueID+"',"+maxlen+")",1);

	}

    if(window.validateInputLenShowProgressEnabled)
		showProgress(input,input.value.len(),maxlen);
}

/**
* 在文本框下显示已用进度条
*/
function showProgress(input,curlen,maxlen)
{   var inputpos = getAbsolutePos(input);
    if(!input.progress)
    {
      var progress = document.createElement("<hr style='position:absolute;color:#FF0000'>");
	  document.body.insertBefore(progress);
	  input.progress = progress;
	  progress.style.left = inputpos.left;
      progress.style.top = inputpos.top + inputpos.height - 2;
	}

	var progress = input.progress;

    progress.style.width = (inputpos.width * curlen / maxlen) ;
}

/**
* 给页面上所有设置了maxlen属性的文本框attach事件onpropertychange，以在value值更改时收到事件通知
*/


function limitTextLength()
{
  //例<input type=text maxlen="6">或 <input type=password maxlen="7">
  var inputs = document.all.tags("INPUT");
  if(typeof(inputs)!="undefined" && inputs!=null && inputs.length!=0)
  {
    for(var i=0;i<inputs.length;i++)
    {
      var input = inputs[i];
      var type = input.type;
      if(typeof(type)=="undefined" || type==null)continue;
      if(type=="text" || type=="password")
      {
        var maxlen = input.getAttribute("maxlen");
        if(typeof(maxlen)=="undefined")continue;
        maxlen = parseInt(maxlen);
        if(isNaN(maxlen))continue;
        input.attachEvent("onpropertychange",text_propertychange);
      }//end if
    }//end for
  }//end if

  //对于textarea限长
  var inputs = document.all.tags("TEXTAREA");
  if(typeof(inputs)!="undefined" && inputs!=null && inputs.length!=0)
  {
    for(var i=0;i<inputs.length;i++)
    {
      var input = inputs[i];
      var maxlen = input.getAttribute("maxlen");
	  if(typeof(maxlen)=="undefined")continue;
	  maxlen = parseInt(maxlen);
	  if(isNaN(maxlen))continue;
	  input.attachEvent("onpropertychange",text_propertychange);
    }
	//end for
  }//end if
}//end function

//当页面load完成时执行limitTextLength函数
if(window.limitInputLenEnabled)
  window.attachEvent("onload",limitTextLength);


/**
* 表单的onsubmit处理函数
* 验证所有的包含regExp属性的文本框，如果不符合，聚焦选中这个文本框，并提示regExpErrorInfo属性设定的值,如不设，则没有提示。
* 如果设置了allowEmpty="true"的话，如果文本框内容空，则直接验证通过。
* 示例： 1.<input type="text" regExp="/^(13|8613)[0135-9][0-9]{8}$/g" regExpErrorInfo="请输入有效的手机号码">
        2.<input type="text" regExp="^(13|8613)[0135-9][0-9]{8}$" allowEmpty="true">
		如果输入的内容不是有效的手机号，当表单提交时自动取消提交动作，例1还会提示请输入有效的手机号码。
*/
function form_submit()
{
  var form = event.srcElement;
  for(var i=0;i<form.elements.length;i++)
  {
	  var element = form.elements[i];
      if(element.type=="text" || element.type=="password" || element.type=="textarea")
	  {
         var regExp = element.getAttribute("regExp");
		 if(regExp==null || regExp=="")
			 continue;
		 try{
			var value = element.value;

			var allowEmpty = element.getAttribute("allowEmpty");

			//如果明确表示允许为空而且当前值为空的话，则直接通过
			if(allowEmpty=="true" && value=="")continue;

			//如果明确表示不允许为空且当前值为空的话，则直接禁止
			if(allowEmpty=="false" &&value=="")
			{
				element.focus();
				element.select();

				var emptyInfo = element.getAttribute("emptyInfo");
				if(emptyInfo!=null)
					alert(emptyInfo);

				return false;
			}

			//验证正则表达式
			var reg = null;
			if(regExp.charAt(0)=="/")
				reg = eval(regExp);
			else
				reg = new RegExp(regExp,"g");
			var matched = value.match(reg);
			if(!matched)
			{
			  element.focus();
			  element.select();

			  var regExpErrorInfo = element.getAttribute("regExpErrorInfo");
			  if(regExpErrorInfo==null)continue;
			  alert(regExpErrorInfo);
			  return false;
			}
		 }catch(ex)
		  {

		  }

	  }

  }
}

function window_onload1()
{
  if(!document.forms)return;
  for(var i=0;i<document.forms.length;i++)
  {
	  var form = document.forms[i];
	  form.attachEvent("onsubmit",form_submit);
  }
}

if(window.validateInputEnabled)
  window.attachEvent("onload",window_onload1);



function getInfoPanel()
{
  if(!window.infoPanel)
  {
      var infoPanel = document.createElement("<div style='position:absolute;z-index:2;height:2px;border:solid 1px;background-color:#FCFCFC'></div>");
	  document.body.insertBefore(infoPanel);
	  window.infoPanel = infoPanel;
  }

  return window.infoPanel;
}
/**
* 当有title属性的表单元素得到焦点时的处理函数
* 例：<input type="text" title="试一试">
*/
function element_onfocus()
{
  var element = event.srcElement;

  var title = element.getAttribute("title");

  var objpos = getAbsolutePos(element);
  var infoPanel = getInfoPanel();
  infoPanel.style.left = objpos.left + objpos.width + 2;
  infoPanel.style.top = objpos.top;
  infoPanel.innerText = title;
  infoPanel.style.display = "";
}

function element_onblur()
{
  var infoPanel = getInfoPanel();
  infoPanel.style.display = "none";
}

function window_onload2()
{
  var inputs = document.all.tags("INPUT");
  if(typeof(inputs)!="undefined" && inputs!=null && inputs.length!=0)
  {
    for(var i=0;i<inputs.length;i++)
    {
      var input = inputs[i];
      var type = input.type;
      if(typeof(type)=="undefined" || type==null)continue;
      var title = input.getAttribute("title");
	  if(title==null || title=="")continue;
	  input.attachEvent("onfocus",element_onfocus);
	  input.attachEvent("onblur",element_onblur);
    }//end for
  }//end if
/*  //以下用于控制TEXTAREA的鼠标定位
  var inputs = document.all.tags("TEXTAREA");
  if(typeof(inputs)!="undefined" && inputs!=null && inputs.length!=0)
  {
    for(var i=0;i<inputs.length;i++)
    {
      var input = inputs[i];
      var title = input.getAttribute("title");
	  if(title==null)continue;
	  input.attachEvent("onfocus",element_onfocus);
	  input.attachEvent("onblur",element_onblur);
    }//end for
  }*///end if}
}

if(window.showTitleEnabled)
  window.attachEvent("onload",window_onload2);
