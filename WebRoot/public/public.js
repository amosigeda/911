String.prototype.trim = function()
{
  return this.replace(/(^\s+)|(\s+$)/g,"");
}

String.prototype.len = function()
{
  return this.replace(/[\u00FF-\uFFFF]/g,"aa").length;
}

/**����**/
function gotoSort(frm,atCol,sort){
	
	var fm = eval(frm);
	fm.sort.value = sort;
	fm.orderBy.value = atCol;
	if(isNaN(fm.index.value)){
		fm.index.value = "1";
	}
	if(isNaN(fm.pageSize.value)){
		fm.pageSize.value = "20";
	}
	fm.submit();
}
/**��ҳ**/
function gotoPage(frm,value){	
	var fm = eval(frm);
	fm.index.value = value;
	/*����Ϊ���֣�������ΪĬ��ֵ*/
	if(isNaN(fm.index.value)){
		fm.index.value = "1";
	}
	if(parseInt(value) > parseInt(fm.pageCounts.value)){
		fm.index.value = "1";
	}
	if(isNaN(fm.pageSize.value)){
		fm.pageSize.value = "20";
	}
	fm.submit();
}

function selectAllRow(){
	if(document.all.crow == null)return false;
	if(document.all.crow.length>0)
	{
		if(document.all.allrow.checked)
		{
			for(i=0;i<document.all.crow.length;i++)
			{
				document.all.crow[i].checked=true;
				document.all.crow[i].onclick=function ()
				{
					var i=0;
					if (document.all.allrow.checked)
					{
						document.all.allrow.checked=false;
					}
					else
					{
						for (var j=0;j<document.all.crow.length;j++)
						{
							if (document.all.crow[i].checked)
							{
								i++
							}
						}
						if (i==document.all.crow.length)
						{
							document.all.allrow.checked=true;
						}
					}
				};
			}
		}
		else
		{
			for(i=0;i<document.all.crow.length;i++)
			{
				document.all.crow[i].checked=false;
			}
		}
	}
	else
	{
			//alert(document.all.crow.length);
			if(document.all.allrow.checked)
			{
				document.all.crow.checked=true;
			}
			else
			{
				document.all.crow.checked=false;
			}		
	}
}

function selectRow()
{
		var i=0;
		for (var j=0;j<document.all.crow.length;j++)
		{
			if (document.all.crow[i].checked)
			{
				i++
			}
		}
		if (i==document.all.crow.length)
		{
			document.all.allrow.checked=true;
		}
		else
		{
			document.all.allrow.checked=false;
		}
}

function countBox()
{
	var n = 0;
	var box = document.all.crow;
    
	if(box == null) return 0;
	if(typeof(box.length) == 'undefined'){
		return box.checked ? 1 : 0;
	}

//alert(box.length);

	for(i=0;i<box.length;i++)
	{
	 if(box[i].checked){
		n=n+1;
	  }
	}
   return n;

}


//////////////////////////////////////////////////////
function checkMobile(mb){
	if(mb.trim() == ''){
		return false;			 
	}
	if(mb.match(/^13[0-9][0-9]{8}$/gi) ||
		mb.match(/^15[0-9][0-9]{8}$/gi) ||
		mb.match(/^18[0-9][0-9]{8}$/gi) ) {
	}else{
		return false;			 
	}
	return true;
}


function checkMobiles(cons){
	if(cons == null) return false;
	var strs = cons.value;
	if(strs.trim() == ''){
		return false;			 
	}
	var l = strs.trim().len();
	var mobileL = 10;
	if(l < 11){//�������?
		return false;
	}else if( l == 11){
		if(cons.value.match(/^13[0135-9][0-9]{8}$/gi) ||
			cons.value.match(/^15[0135-9][0-9]{8}$/gi) )	{
 		}else{
			return false;			 
		}
	}
	/*while(strs.indexOf("��") > -1){//�滻��д�Ķ���
		strs = strs.replace("��",",");
	}
	while(strs.indexOf(",,") > -1){//�滻�ظ��Ķ���
		strs = strs.replace(",,",",");
	}
	if(strs.indexOf(",") == 0){//ȥ���һ���
		strs = strs.substring(1);
	}
	if(strs.lastIndexOf(",") == strs.len() -1){//ȥ�����һ���
		strs = strs.substring(0,strs.len()-1);
	}*/
	//alert(strs);
	strs = replaceMe(strs);
	//alert(strs);
	var ary = strs.split(",");
	//�ֻ����һ���?1λ���ֻ�+86 ��13λ�����������ֻ�ʺ�?1λ
	for(var i = 0; i < ary.length;i++){
		var s = ary[i];
		if(s.match(/^13[0135-9][0-9]{8}$/gi) ||
			s.match(/^15[0135-9][0-9]{8}$/gi) )	{
 		}else{
			return false;			 
		}
	}
	cons.value = checkSame(strs,",");
	return true;
}
//��������û�?
function checkSames(cons){
	if(cons == null) return false;
	var strs = cons.value;
	if(strs.trim() == ''){
		return false;			 
	}
	strs = replaceMe(strs);
	cons.value = checkSame(strs,",");
	return true;
}

function replaceMe(str){
	//alert(str);
	str = replaceAll(str,"��",",");//�滻��д
	//alert(str);
	str = replaceAll(str,",,",",");//�滻�ظ�
	//alert(str);
	str = removeStartAdEnd(str,",");
	//alert(str);
	return str;
}

/////////////////////////////////////////////////
//�˺������֮ǰȷ��û���ظ���str1
function checkSame(str,str1){
	//alert(str);
	var ary = str.split(str1);
	var v = ArrayToSet(ary);
	var s = "";
	for(var i=0;i<v.length;i++){
		if( s == ""){
			s = v[i];
		}else{
			s = s + "," + v[i];
		}
	}
	return s;
}

function   ArrayToSet(arr){   
	function   contains() {   
		for(var   i=0;i<r.length;i++)   
			if(r[i]==o) return   true;   
	}   
    
	var r=[];   
	for(var   i=0;i<arr.length;i++) {   
		var   o=arr[i];   
		if(contains()) 	continue;   
			r[r.length]=o;   
	}   
	return   r;   
  }   
    
//�滻�ַ���
function replaceAll(str,str1,str2){
	if(str.trim() == "")return str;
	while(str.indexOf(str1) > -1){//�滻��д�Ķ���
		str = str.replace(str1,str2);
	}
	return str;
}

//ȥ����λ�ַ�
function removeStartAdEnd(str,str1){
	var s = str.trim();
	if(s == "")return s;
	if(s.len() == 1 && s.indexOf(str1) == 0) {
		return "";
	}else{
		if(s.indexOf(str1) == 0){//ȥ���һ���
			s = s.substring(1);
		}
		if(s.len() == 1 && s.indexOf(str1) == 0) {
			return "";
		}else{
			if(s.lastIndexOf(",") == s.len() -1){//ȥ�����һ���
				s = s.substring(0,s.len()-1);
			}
		}
		return s;
	}
}


////////////////////////////////////////
//��select ����һ�� 
function addSelect(con,t,v){
	if(con == null) return;
	if(t == "")alert("�ı�Ϊ��");
	if(v == "")alert("IDΪ��");
	var newOption = new Option(t ,v);
	var l = con.length;
	if( l == 0){
		con.add(newOption);
	}else{
		var is = false;
		for(var i = 0; i < l;i ++){
			var v1 = con.options[i].value;
			if(v1 == v){
				is = true;
			}
		}
		if(!is)con.add(newOption);
	}
}
/*ɾ��ѡ����*/
function removeSelect(con){
	if(con == null) return;
	var item;
	var srcItem = con;
	for (var i=srcItem.length-1;i>=0;i--)
	{
		item = srcItem.item(i);
		if ( item.selected )
		{
			srcItem.remove(i);
		}
	}
}

//���select�ö�����֯�ַ�
function getSelectStrs(con){
	if(con == null) return "";
	var s = "";
	for(var i = 0; i < con.length;i ++){
		var v1 = con.options[i].value;
		if(s == ""){
			s = v1;
		}else{
			s = s + "," + v1;
		}
	}
	return s;
}
//�ѷָ�õ��ַ���ӵ�select
function setSelectStrs(con,str,splitstr){
	if(str == "" || str == "null")return;
	var ary = str.split(splitstr);
	for(var i=0;i<ary.length;i++){
		var s = ary[i];
		addSelect(con,s,s);
	}

}
//select�ؼ���λs�ı�
function setSelected(con,s){
	for(var i = 0; i < con.length;i ++){
		var v = con.options[i].value;
		if(v == s){
			//alert(v);
			con.options[i].selected = true;
			return;
		}
	}
}
//ʵ�ֵ���ڵ�tab����
function aTabDown(z)
{
	if(event.keyCode==40)
	{
		var te = eval("document.all.ah" + (z + 1));
		if(te != undefined)te.focus();
	}
	if(event.keyCode==38)
	{
		if(z-1 == -1)return;
		var te = eval("document.all.ah" + (z - 1));
		if(te != undefined)te.focus();
	}
}
//ʵ�ֵ���ڵ����ҹ���?
function aTabLR()
{
	if(event.keyCode==37)
	{
		var indx = document.all.index.value;
		if(indx - 1 != 0)gotoPage('frmGo',indx - 1);
	}
	if(event.keyCode==39)
	{
		var indx = (document.all.index.value);
		var ii = parseInt(indx);
		var cs = document.all.pageCounts.value;
		if((ii + 1) <= cs)gotoPage('frmGo',(ii + 1));
	}
}
//<body onkeydown="alert(event.keyCode)">

///��������ʱ��ȵ�ǰʱ��?niusС�򷵻�false
function dateToMius(str,mius){
	if(str.indexOf("-") > -1)str = replaceAll(str,"-","/")//ת���ַ�
	var d = Date.parse(str);
	var nd = new Date().getTime() + mius * 1000 * 60;
	if( d < nd) return false;
	return true;
}
//check�ı������볤��
function checkTxtLength(con,size){
	var str = con.value;
		if(str.length > size)
		{
			alert('�ı�����Ӧ������' + size);
			con.value = str.substr(0,size);
			con.focus();
			return false;
		}

}


var request = false;
   try {
     request = new XMLHttpRequest();
   } catch (trymicrosoft) {
     try {
       request = new ActiveXObject("Msxml2.XMLHTTP");
     } catch (othermicrosoft) {
       try {
         request = new ActiveXObject("Microsoft.XMLHTTP");
       } catch (failed) {
         request = false;
       }  
     }
   }
   if (!request)
     alert("Error initializing XMLHttpRequest!");


function ajaxSubmitForm(form) {
	var elements = form.elements;// Enumeration the form elementsvar element;
	var i;
	// Form contents need to submit
	for(i=0;i<elements.length;++i) {
		var element=elements[i];
		if(element.type=="text" || element.type=="textarea" || element.type=="hidden") {
			postContent += encodeURIComponent(element.name) + "=" + encodeURIComponent(element.value) + "&";
		}else if(element.type=="select-one"||element.type=="select-multiple") {
			var options=element.options,j,item;
			for(j=0;j<options.length;++j){
				item=options[j];
				if(item.selected) {
					postContent += encodeURIComponent(element.name) + "=" + encodeURIComponent(item.value) + "&";
				}
			}
		} else if(element.type=="checkbox"||element.type=="radio") {
			if(element.checked) {
				postContent += encodeURIComponent(element.name) + "=" + encodeURIComponent(element.value) + "&";
			}
		} else if(element.type=="file") {
			if(element.value != "") {
				postContent += encodeURIComponent(element.name) + "=" + encodeURIComponent(element.value) + "&";
			}
		} else {
			postContent += encodeURIComponent(element.name) + "=" + encodeURIComponent(element.value) + "&";
		}
	}
	postContent = encodeURI(postContent);
	//postContent = encodeURI(postContent);
	//alert(postContent);
	//ajaxSubmit(form.action, form.method, postContent);
}


/////////////////////////////////
function ckChickBoxs(obj,is){
	var boxs = document.all.ckbox;
	var lg = boxs.length;
	//alert(lg);
	for(var i = 0; i < lg; i++){
		var cbox = boxs[i];
		if(obj.value == cbox.superCode){
			cbox.checked = is;
			ckChickBoxs(cbox,is);
		}
	}
}

function ckSuperBoxs(obj,is){
	var boxs = document.all.ckbox;
	var lg = boxs.length;
	for(var i = 0; i < lg; i++){
		var cbox = boxs[i];
		if(obj.superCode == cbox.value){
			cbox.checked = is;
			ckSuperBoxs(cbox,is);
		}
	}
}

function ckBoxsStr(){
	var boxs = document.all.ckbox;
	var lg = boxs.length;
	var str = "";
	for(var i = 0; i < lg; i++){
		var cbox = boxs[i];
		if(cbox.checked == true){
			if(str == ""){
				str = cbox.value;
			}else{
				str = str + "," + cbox.value;
			}
		}
	}
	return str;
}


//  ���� tbl  �ƶ���ʽ//
var  highlightcolor='#eafcd5';
var  clickcolor='#51b2f6';
function  tblChangeToColor(){
	source=event.srcElement;
	if  (source.tagName=="TR"||source.tagName=="TABLE"){
		return;
	}

	while(source.tagName!="TD")
		source=source.parentElement;
	
	source=source.parentElement;
	cs  =  source.children;

	for(i=0;i<cs.length;i++){
		if(cs[i].style.backgroundColor == highlightcolor){
			cs[i].style.backgroundColor = "";
		}else{
			cs[i].style.backgroundColor=highlightcolor;
		}
	}
}

function isNumber(oNum){
	if(!oNum) return false;
	var strP=/^\d+(\.\d+)?$/;
	if(!strP.test(oNum)) return false;
	try{
		if(parseFloat(oNum)!=oNum) return false;
	}
	catch(ex)
	{
		return false;
	}
		return true;
 } 
