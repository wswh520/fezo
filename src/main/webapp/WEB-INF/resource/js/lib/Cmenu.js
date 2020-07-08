var actpath = "./ejld.php";		//联动菜单路径
var isIE6= navigator.appVersion.indexOf("MSIE 6")>-1;	//判断是否IE6，IE6去死....

$.fn.extend({
	seleFn:function(){
		$(this).each(function(){
			var _method=$(this),_parent=null;
			if(_method.parent('label').attr('cmenu')=='true'||_method.attr('cmenu')=='true') return false;
			var	len = $(this).children("option").length, str = "",
				w = $(this).width()+parseInt($(this).css('padding-right'))+parseInt($(this).css('padding-left')),
				stype= ($(this).attr('stype')=='liandong')?"stype='liandong'":"",
				ldname=($(this).attr('stype')=='liandong')?"ldname='"+$(this).attr('ldname')+"'":"",
			    border  = "<span class='seleJs'><span class=\"seleTB\" "+stype+ldname+"></span><div class=\"apps_seleMenu\"></div></span>";
			_method.hide().before(border);
			_method.attr('cmenu','true');
			var _seleMenu=_method.prev().find('.apps_seleMenu');
			_seleMenu.html(_method.html().replace(/option/gi,'p').replace(/optgroup/gi,'h5'));<!--2014-6-10 修改 "w-8"改成“w-1” “.width(_seleMenu.width()>w-1?_seleMenu.width()+48:w-1)”-->
			_seleMenu.find('h5').each(function(){$(this).html($(this).attr('label'))});
			_seleMenu.find('p').each(function(){if('disabled'==$(this).attr('disabled')) $(this).addClass('undis')});
			if(_seleMenu.height()>130){_seleMenu.css({'overflow-y':'auto','height':130,'width':_seleMenu.width()+16,'border-top':'1px solid #d5d5d5'});}<!--  2014-6-10 添加了 'border-top':'1px solid #d5d5d5'  -->
			var _seleTB=_method.prev().find('.seleTB');
			_seleTB.width(w-24).html(_method.children("option:selected")?_method.children("option:selected").html():$(this).children("option")[0].innerHTML);
		})//end each
		
		var _seleMenu=$('.apps_seleMenu'),_seleMenuP=$('.apps_seleMenu p'),_seleTB=$('.seleTB'),
		CreatS={
			init:function(){
				this.seleJsFun();
				this.linkAge();
			},
			seleJsFun:function(){
				$("body").unbind().bind('click',function(e){_seleMenu.hide();})
				_seleMenuP.hover(function(){if('disabled'!=$(this).attr('disabled')) $(this).addClass('on');},function(){$(this).removeClass('on');});
				_seleTB.unbind('click').bind('click',function(e){
					e.stopPropagation();
					_seleMenu.hide();$(this).next().toggle();$('.seleJs').css('z-index',0);
					$(this).parent().css('z-index',999);
				})
				_seleMenuP.each(function(){
					if('disabled'==$(this).attr('disabled')) return;
					$(this).die().live("click",function(){
					var seleIndex = $(this).index(),_seleTB=$(this).closest('.seleJs').find('.seleTB'),
						_method=$(this).closest('.seleJs').next(),_seleMenu=$(this).parent();
					_seleTB.html($(this).html()).attr("tvalue",$(this).attr("value")).trigger('change');
					_method[0].selectedIndex = seleIndex;
					_method.trigger('change');
					_seleMenu.hide();
					})
				})
			},
			linkAge:function(){
				var _ld=$("span[stype='liandong']"),	
					txt=["--请选择--","<p value=''>--请选择--</p>","<option value=''>--请选择--</option>"];
				if(_ld.length==0) return false;
				_ld.unbind('change').bind('change',function(){
					var ldname=$(this).attr('ldname');
					var	ldgroup=$("span[ldname='"+ldname+"']");
					var	ldlen=$("span[ldname='"+ldname+"']").length;
					var	ldindex=$("span[ldname='"+ldname+"']").index($(this));
					var	nsel=ldgroup.eq(ldindex+1);
					var	tvalue=$(this).attr('tvalue');
					if(""==tvalue){
						for(var i=ldindex+1;i<ldlen;i++){ ldgroup.eq(i).html(txt[0]).next().html(txt[1]);}
						return false;
					}
					$.ajax({
					   type: "POST",url: actpath,data: "value="+tvalue,dataType :"json",async: false,
					   success: function(msg){
						nsel.html(txt[0]).next().html(txt[1]).parent().next().html(txt[2]);
						for(var i in msg){
							var p=document.createElement('p');
							nsel.next().append('<p value="'+msg[i][0]+'">'+msg[i][1]+'</p>');
							nsel.parent().next().append('<option value="'+msg[i][0]+'">'+msg[i][1]+'</option>');
						};
						nsel.next().width('auto');
						nsel.next().find("p").bind("click",function(){
							var nowObj=$(this).parent().prev(),seleObj=$(this).closest('.seleJs').next();
							var seleIndex = $(this).index();
							seleObj[0].selectedIndex = seleIndex;
							seleObj.trigger('change');
							nowObj.html($(this).html()).attr("tvalue",$(this).attr("value")).trigger('change');
							$(this).parent().hide();
						}).hover(function(){$(this).addClass('on');},function(){$(this).removeClass('on');});
					   }//end success
					})//end ajax
				})//end change
			}//end linkAge
		}
		CreatS.init();
	},
	radioFn:function(){
		$(this).each(function(){
			var _method=$(this);
			if(_method.parent('label').attr('cmenu')=='true'||_method.attr('cmenu')=='true') return false;
			_method.parent().attr('cmenu','true');
			_method.parent().hide().before("<label name=\""+$(this).attr("name")+"\" class=\"radioTb\"><span>"+_method.parent().text()+"</span></label>");
			var _parent=_method.parent().prev();
			var str=_method.attr("checked")=="checked"?"radioBtn_on":"radioBtn";
			var disable=_method.attr("disabled")=="disabled"?" disabled=\"disabled\"":"";
			_parent.prepend("<span class='"+str+"' "+disable+"></span>");
			_parent.hover(function(){_method.css({"cursor":"pointer"})},function(){_method.css({"cursor":"normal"})});
			_parent.unbind().bind('click',function(){
				var obj=_parent.find("span").eq(0);
				var nm=$(this).attr("name");
				if(obj.attr("disabled")=="disabled"){ return false;}
				$(".radioTb").each(function(){
					if($(this).attr('name')==nm){
						$(this).find("span:eq(0):not([disabled='disabled'])").attr('class','radioBtn');
					}
				})
				obj.attr('class',obj.attr('class')=='radioBtn'?'radioBtn_on':'radioBtn');	
				_method.click();//.attr('checked',true);		
			})
		})//end each
	},
	checkboxFn:function(){
		$(this).each(function(){
			var _method=$(this);
			if(_method.parent('label').attr('cmenu')=='true'||_method.attr('cmenu')=='true') return;//return false;
			_method.parent().hide().before("<label name=\""+_method.attr("name")+"\" class=\"ckboxTb\"><span>"+_method.parent().text()+"</span></label>");
			_method.parent().attr('cmenu','true');
			var _parent=_method.parent().prev();
			var str=_method.attr("checked")=="checked"?"ckboxBtn_on":"ckboxBtn";
			var disable=_method.attr("disabled")=="disabled"?" disabled=\"disabled\"":"";
			_parent.prepend("<span class='"+str+"' "+disable+"></span>");
			_parent.hover(function(){_method.css({"cursor":"pointer"})},function(){_method.css({"cursor":"normal"})});
			_parent.unbind().bind('click',function(){
				var obj=_parent.find("span").eq(0);
				if(obj.attr("disabled")=="disabled"){ return false;}
				obj.attr('class',obj.attr('class')=='ckboxBtn'?'ckboxBtn_on':'ckboxBtn');	
				_method.attr('checked',obj.attr('class')=='ckboxBtn'?false:true);		
			})
		})
	},
	allCheckCmenu:function(_obj){
		$("input[type='checkbox']").checkboxFn();
		var _method=$(this);
		_method.prev().click(function(){
			if('checked'==_method.find("input").attr('checked')){
				_obj.attr('checked','checked');
				_obj.each(function(){$(this).parent().prev().find('span').eq(0).attr('class','ckboxBtn_on');})
			}else{
				_obj.attr('checked',false);
				_obj.each(function(){$(this).parent().prev().find('span').eq(0).attr('class','ckboxBtn');})
			}
		})
	},
	schSeleFn:function(){
		$(this).each(function(){
			if('none'==$(this).css('display')) return false;
			var _method=$(this),_parent=null;
				len = $(this).children("option").length, str="",
				w = $(this).width(),
			    border  = "<span class=\"apps_fn_schTB\"><span class=\"selshow\" name=\"selshow\" readonly=\"readonly\" ></span><div class=\"apps_fn_schMenu\"></div></span>";
				for(var i=0; i<len; i++){
					var fs=(i==0)?" class='f' ":"",ls=(i==len-1)?" class='l' ":"";
					str += "" != $(this).children("option")[i].innerHTML?"<p "+fs+ls+"><span>"+_method.children("option")[i].innerHTML+"</span></p>":"";
				}
			_method.hide().before(border);
			_method.prev().find('.selshow').html(_method.children("option:selected")?_method.children("option:selected").html():$(this).children("option")[0].html())
			_method.prev().find(".apps_fn_schMenu").html(str);
			var _parent=_method.prev(".apps_fn_schTB"),
				_showO = _parent.find(".selshow"),
				_schMenu=_parent.find('.apps_fn_schMenu'),
				creatSch={
				init:function(){
					_parent.width(w);
					_schMenu.width(w);
					this.schJsFun();
				},
				schJsFun:function(){
					$("body").click(function(){ $(".apps_fn_schMenu").hide(); })
					_parent.unbind('click').bind('click',function(e){e.stopPropagation();_schMenu.toggle();})
					_schMenu.find("p").hover(function(){$(this).addClass('on');},function(){$(this).removeClass('on');});
					_schMenu.find("p").unbind("mousedown").bind("mousedown",function(e){
						e.stopPropagation();
						_showO.html($(this).find("span").html()).attr("tvalue",$(this).attr("value"));
						_schMenu.hide().trigger('change');
						_method[0].selectedIndex = $(this).index();
						_method.trigger('change');
					})
				}
			}
			creatSch.init();
		})
	},
	fileTxFn:function(){
		$(this).each(function(){
			if(0==$(this).width()) return false;
			var _method=$(this),w=$(this).width();
			if(isIE6) $(this).css('display','none');
			$(this).height(0).width(0).before("<span class='apps_fn_fileCss'><input type='text' class='inp' value='"+$(this).val()+"' readonly='readonly' /> <input class='btn_blue' type='button' value='上传'></span>");
			var _parent=_method.prev('span');
			_parent.unbind().bind('click',function(){_method.click();return false;})
			_method.unbind('change').bind('change',function(){_parent.find("input[type='text']").val(_method.val());return false;})
		})
	}
})

$(document).ready(function(){
	$('select.seleCss').seleFn();
	$("input[type='radio']").radioFn();
	$("input[type='checkbox']").checkboxFn();
	$("select.schSele").schSeleFn();
	//$("input[type='file'].fileCss").fileTxFn();
})




