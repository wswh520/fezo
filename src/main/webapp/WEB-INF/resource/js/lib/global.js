"use strict"
$.fn.extend({
	//输入框字符显示
	showWord:function(){
		var str=$(this).val();
		var _c=$(this).css('color');
		$(this).focus(function(){
			if(str == $(this).val()){ 
				$(this).val('');
				$(this).css('color','#000');
			}
		})
		$(this).blur(function(){
			if("" == $(this).val()){
				 $(this).val(str);
				 $(this).css('color',_c);
			}
		})
	},
	//end 输入框字符显示
	
	//图片轮播样式一
	mPicFlash:function(obj){
			var _method=this,
			flash={
				index:0,
				state:obj&&obj.state&&obj.state=='top'?'top':'left',
				speed:obj&&obj.speed&&typeof obj.speed==='number'?obj.speed:3000,
				title:obj&&obj.title&&obj.title===true?true:false,
				type:obj&&obj.type&&obj.type==='fade'?'fade':'scroll',
				t:0,b:0,c:0,d:40,timer:null,
				init:function(){
					var method=this;
					this.width=_method.width();
					this.height=_method.height();
					this.length=_method.find('ul li').length;
					this.timer=null;
					this.change={
						float:this.state=='left'?'left':'none',
						ulCss:this.state=='left'?{'width':this.width*this.length}:{'height':this.height*this.length}
					};
					_method.css({'width':this.width,'height':this.height}).find('ul,img').css({'width':this.width,'height':this.height,'float':this.change.float});
					_method.find('ul').css(this.change.ulCss);
					method.type=='fade'?_method.find('ul li').css({'position':'absolute','left':0,'top':0}):false;
					
					var html=this.title?'<p></p><span class="tit"></span><ol>':'<ol>';
					for(var i=0;i<this.length;i++){html+='<li>'+eval(i+1)+'</li>';};
					_method.append(html+'</ol>');
					
					this.picChange();
					this.bindFn();
				},
				picChange:function(){
					var method=this;
					_method.find('ol li').eq(this.index).addClass('on').siblings().removeClass('on');
					if(method.type=='fade'){
						_method.find('ul li').css('z-index',0).fadeOut(500);
						_method.find('ul li').eq(this.index).css('z-index',10).fadeIn(500);
					}
					else{
						_method.find('ul').stop(true,false).animate(this.state=='left'?{left:-1*this.width*this.index}:{top:-1*this.height*this.index},{duration: 1000, easing: "easeOutQuart"});
						/*缓动
						method.t=0;
						if(method.state=='left'){
							method.c=-1*method.width*method.index-parseInt(_method.find('ul').css('left'));
							method.b=parseInt(_method.find('ul').css('left'));
						}else{
							method.c=-1*method.height*method.index-parseInt(_method.find('ul').css('top'));
							method.b=parseInt(_method.find('ul').css('top'));
						}
						method.timer=setInterval(flash.GoRun,20);
						*/
					}
					_method.find('span').stop(true,false).fadeTo(300,0,function(){
						_method.find('span').fadeTo(300,1);
					});
					_method.find('span').html('<a href="'+_method.find('ul a').eq(this.index).attr('href')+'">'+_method.find('ul a img').eq(this.index).attr('title')+'</a>');
				},
				/*GoRun:function(){//缓动
					 if(flash.t<=flash.d){ 
					 	flash.state=='left'?
							_method.find('ul').css('left',Math.ceil(Tween.Quart.easeOut(flash.t,flash.b,flash.c,flash.d))):
							_method.find('ul').css('top',Math.ceil(Tween.Quart.easeOut(flash.t,flash.b,flash.c,flash.d)));
						flash.t++;
					}else{
						clearInterval(flash.timer);
					}
				},*/
				bindFn:function(){
					var method=this;
					_method.find('ol li').mouseover(function(){
						if(advPlay){clearInterval(advPlay);}
						if(flash.timer){clearInterval(flash.timer);}
						if(method.index==$(this).index()){return false;}
						method.index=$(this).index();
						method.picChange();
					});
					_method.find('ol li').mouseout(function(){
						advPlay=setInterval(function(){
							method.setTimeFn();
						},method.speed);
					});
					var advPlay=setInterval(function(){
						method.setTimeFn();
					},method.speed);
				},
				setTimeFn:function(){
					this.index++;
					if(this.index==this.length){this.index=0;}
					this.picChange();
				}
			};
			flash.init();
	},
	//end 图片轮播样式一
	
	//图片轮播样式二
	mPicFlash2:function(obj){
		var _method=this,
		flash={
			index:0,
			state:obj&&obj.state&&obj.state=='top'?'top':'left',
			speed:obj&&obj.speed&&typeof obj.speed==='number'?obj.speed:3000,
			title:obj&&obj.title&&obj.title===true?true:false,
			init:function(){
				this.width=_method.width();
				this.height=_method.height();
				this.length=_method.find('ul li').length;
				this.change={
					float:this.state=='left'?'left':'none',
					ulCss:this.state=='left'?{'width':this.width*this.length}:{'height':this.height*this.length}
				};

				_method.css({'width':this.width,'height':this.height}).find('ul,li,img').css({'width':this.width,'height':this.height});
				_method.find('ul').css(this.change.ulCss);
				
				var html=this.title?'<p style="width:100%; height:82px; background:#000; position:absolute; bottom:0; left:0; z-index:11; filter:alpha(opacity=70); opacity:0.7;"></p>':'';
				html+=this.title?'<span style="width:100%; height:25px; font-size:14px; line-height:25px; overflow:hidden; padding-left:10px; position:absolute; bottom:57px; left:0; z-index:12;"></span>':'';
				html+='<ol style="color:#fff; position:absolute; bottom:4px; right:0; z-index:13;">';
				for(var i=0;i<this.length;i++){
					html+='<li style="width:111px; height:43px; background:#fff; cursor:pointer; float:left; margin-right:3px;position:relative; padding:2px;background:#6b7074;"><img src="'+_method.find('ul a img').eq(i).attr('smallsrc')+'" style="width:111px; height:43px;" /></li>';
				};
				_method.append(html+'</ol>');
				this.picChange();
			},
			GO:function(){
				this.init();
				this.bindFn();
			},
			picChange:function(){
				_method.find('ol li').eq(this.index).css({'opacity':'1','background':'url(./images/scrolBg.png)','padding-top':'6px','bottom':'0'}).siblings().css({'opacity':'0.6','background':'#6b7074','height':'43px','padding-top':'2px','bottom':'-4px'});
				_method.find('ul').stop(true,false).animate(this.state=='left'?{left:-1*this.width*this.index}:{top:-1*this.height*this.index},600);

				_method.find('span').stop(true,false).fadeTo(300,0,function(){
					_method.find('span').fadeTo(300,1);
				});
				_method.find('span').html('<a href="'+_method.find('ul a').eq(this.index).attr('href')+'" style="color:#fff; text-decoration:none;">'+_method.find('ul a img').eq(this.index).attr('title')+'</a>');
			},
			bindFn:function(){
				var method=this;
				_method.find('ol li').mouseover(function(){
					if(advPlay){clearInterval(advPlay);}
					if(method.index==$(this).index()){return false;}
					method.index=$(this).index();
					method.picChange();
				});
				_method.find('ol li').mouseout(function(){
					advPlay=setInterval(function(){
						method.setTimeFn();
					},method.speed);
				});
				var advPlay=setInterval(function(){
					method.setTimeFn();
				},method.speed);
			},
			setTimeFn:function(){
				this.index++;
				if(this.index==this.length){this.index=0;}
				this.picChange();
			}
		};
		flash.GO();
	},
	//end图片轮播样式二
	
	//图片轮播样式三
	mPicFlash3:function(obj){
		var _method=this,
		flash={
			index:0,
			state:obj&&obj.state&&obj.state=='top'?'top':'left',
			speed:obj&&obj.speed&&typeof obj.speed==='number'?obj.speed:3000,
			title:obj&&obj.title&&obj.title===true?true:false,
			init:function(){
				this.width=_method.width();
				this.height=_method.height();
				this.length=_method.find('ul li').length;
				this.change={
					float:this.state=='left'?'left':'none',
					ulCss:this.state=='left'?{'width':this.width*this.length,'position':'absolute','z-index':'10'}:{'height':this.height*this.length,'position':'absolute','z-index':'10'}
				};

				_method.css({'width':this.width,'height':this.height,'float':'left','overflow':'hidden','position':'relative'}).find('ul,li,img').css({'width':this.width,'height':this.height,'border':'none','float':this.change.float,'overflow':'hidden'});
				_method.find('ul').css(this.change.ulCss);
				
				var html=this.title?'<p style="width:100%; height:75px; background:#000; position:absolute; bottom:0; left:0; z-index:11; filter:alpha(opacity=60); opacity:0.6;"></p>':'';
				html+=this.title?'<span style="width:280px; height:75px; font-size:18px; font-weight:bold; line-height:75px; overflow:hidden; padding-left:10px; position:absolute; bottom:0; left:0; z-index:12;"></span>':'';
				html+='<ol style="color:#fff; position:absolute; bottom:7px; right:0; z-index:13;">';
				for(var i=0;i<this.length;i++){
					html+='<li style="width:85px; height:55px; background:#fff; cursor:pointer; float:left; margin-right:10px; padding:3px;"><img src="'+_method.find('ul a img').eq(i).attr('smallsrc')+'" style="width:85px; height:55px;" /></li>';
				};
				_method.append(html+'</ol>');
				this.picChange();
			},
			GO:function(){
				this.init();
				this.bindFn();
			},
			picChange:function(){
				_method.find('ol li').eq(this.index).css({'opacity':'1'}).siblings().css({'opacity':'0.6'});
				_method.find('ul').stop(true,false).animate(this.state=='left'?{left:-1*this.width*this.index}:{top:-1*this.height*this.index},600);

				_method.find('span').stop(true,false).fadeTo(300,0,function(){
					_method.find('span').fadeTo(300,1);
				});
				_method.find('span').html('<a href="'+_method.find('ul a').eq(this.index).attr('href')+'" style="color:#fff; text-decoration:none;">'+_method.find('ul a img').eq(this.index).attr('title')+'</a>');
			},
			bindFn:function(){
				var method=this;
				_method.find('ol li').mouseover(function(){
					if(advPlay){clearInterval(advPlay);}
					if(method.index==$(this).index()){return false;}
					method.index=$(this).index();
					method.picChange();
				});
				_method.find('ol li').mouseout(function(){
					advPlay=setInterval(function(){
						method.setTimeFn();
					},method.speed);
				});
				var advPlay=setInterval(function(){
					method.setTimeFn();
				},method.speed);
			},
			setTimeFn:function(){
				this.index++;
				if(this.index==this.length){this.index=0;}
				this.picChange();
			}
		};
		flash.GO();
	},
	//end图片轮播样式三
	
	//图片轮播样式四
	hbfocus:function(obj){
		var _method=this.find('.advRound'),
			_methodp=this,
		flash={
			index:1,
			state:obj&&obj.state&&obj.state=='top'?'top':'left',
			speed:obj&&obj.speed&&typeof obj.speed==='number'?obj.speed:5000,
			title:obj&&obj.title&&obj.title===true?true:false,
			licon:obj&&obj.licon&&_methodp.find(obj.licon)?_methodp.find(obj.licon):'',
			ricon:obj&&obj.ricon&&_methodp.find(obj.ricon)?_methodp.find(obj.ricon):'',
			init:function(){
				this.width=_method.width();
				this.height=_method.height();
				this.length=_method.find('ul li').length;
				this.change={
					float:this.state=='left'?'left':'none',
					ulCss:this.state=='left'?{'width':this.width*this.length,'position':'absolute','z-index':'10'}:{'height':this.height*this.length,'position':'absolute','z-index':'10'}
				};

				_method.css({'width':this.width,'height':this.height,'overflow':'hidden','position':'relative'}).find('ul,li,img').css({'width':this.width,'height':this.height,'border':'none','float':this.change.float,'overflow':'hidden'});
				_method.find('ul').css(this.change.ulCss);
				
				var html=this.title?'<p class="tit"></p>':'';
				html+=this.title?'<span class="tit"></span>':'';
				html+='<ol><li class="lst"></li>';
				for(var i=0;i<this.length;i++){
					html+='<li style=""><img src="'+_method.find('ul a img').eq(i).attr('smallsrc')+'"  /></li>';
				};
				_method.append(html+'<li class="rst"></li></ol><div id="bor" class="bor"></div>');
				this.picChange();
			},
			GO:function(){
				this.init();
				this.bindFn();
			},
			picChange:function(){
				_method.find('#bor').stop(false,false).animate({'right':31+parseInt(this.length-this.index)*48+'px'},{duration:800, easing: "easeOutQuart"});
				_method.find('ul').stop(true,false).animate(this.state=='left'?{left:-1*this.width*(this.index-1)}:{top:-1*this.height*(this.index-1)},{duration: 800, easing: "easeOutQuart"});

				_method.find('span').stop(true,false).fadeTo(300,0,function(){
					_method.find('span').fadeTo(300,1);

				});
				_method.find('span').html('<a href="'+_method.find('ul a').eq(this.index).attr('href')+'" style="color:#fff; text-decoration:none;">'+_method.find('ul a img').eq(this.index-1).attr('title')+'</a>');
			},
			bindFn:function(){
				var method=this;
				_method.find('ol li:not(:gt('+this.length+')):not(:eq(0))').mouseover(function(){
					if(advPlay){clearInterval(advPlay);}
					if(method.index==$(this).index()){return false;}
					method.index=$(this).index();
					method.picChange();
					return false;
				});
				_method.find('ol li').mouseout(function(){
					advPlay=setInterval(function(){
						method.setTimeFn();
					},method.speed);
					return false;
				});
				method.licon.hover(
					function(){$(this).find('.inner').css({'background':'#17b3ee','cursor':'pointer'});},
					function(){$(this).find('.inner').css({'background':'#d4d4d4','cursor':'auto'});}
				).click(function(){
					if(advPlay){clearInterval(advPlay);}
					method.index=method.index-2;
					method.setTimeFn();
					advPlay=setInterval(function(){method.setTimeFn();},method.speed);
				})
				
				method.ricon.hover(
					function(){$(this).find('.inner').css({'background':'#17b3ee','cursor':'pointer'});},
					function(){$(this).find('.inner').css({'background':'#d4d4d4','cursor':'auto'});}
				).click(function(){
					if(advPlay){clearInterval(advPlay);}
					method.setTimeFn();
					advPlay=setInterval(function(){method.setTimeFn();},method.speed);
				})
				
				var advPlay=setInterval(function(){
					method.setTimeFn();
				},method.speed);
			},
			setTimeFn:function(){
				this.index++;
				if(this.index>=this.length+1){this.index=1;}
				if(this.index<=0) {this.index=this.length;}
				this.picChange();
			}
		};
		flash.GO();
	},
	//end图片轮播样式四
	
	//图片滑动切换
	divMove:function(left,right,cont,tag,maxLen,lrNo,where){
		$(this).each(function(){
			var oLeft=$(this).find('i.'+right),
				oRight=$(this).find('i.'+left),
				oCont=$(this).find(cont),
				oWhere=$(this).find(where),
				w=oCont.find(tag).width()+parseInt(oCont.find(tag).css('margin-right')),
				len=oCont.find(tag).length,
				index=0,_method=this,
				maxL=maxLen?maxLen:1,
				leftNo=lrNo?lrNo+'r':right+'_none',
				rightNo=lrNo?lrNo+'l':left+'_none';
			oCont.width(w*len);
			oLeft.click(function(){
				if($(this).attr('class').indexOf(leftNo)>0){return false;}
				index+=maxLen;
				if(index>0){oRight.removeClass(rightNo);}
				if(index>=len-maxL){$(this).addClass(leftNo);}
				oCont.stop(true,false).animate({'left':-1*index*w},800);
				oWhere.eq(index).addClass('on').siblings().removeClass('on');
			});
			oRight.click(function(){
				if($(this).attr('class').indexOf(rightNo)>0){return false;}
				index-=maxLen;
				if(index>=0){oLeft.removeClass(leftNo);}
				if(index<=0){$(this).addClass(rightNo);}
				oCont.stop(true,false).animate({'left':-1*index*w},800);
				oWhere.eq(index).addClass('on').siblings().removeClass('on');
			});
		});
	},
	//end 图片滑动切换
	
	//选项卡
	tabControl:function(tab,con,mor){
		$(this).each(function(){
			var _method=this;
			$(this).find(tab).click(function(){
				if(this.className=='more'){return false;}
				$(this).addClass('on').siblings().removeClass('on');
				$(_method).find(con).addClass('dis_none');
				$(_method).find(con).eq($(this).index()).removeClass('dis_none');
				//$(_method).find(tab+'.more').removeClass('on');
				return false;
			});
			$(this).find(mor).click(function(){
				window.location.href=$(this).attr('href');
			});
		});
	},
	//end 选项卡
	
	//弹出框
	jumpBox:function(style){
		var _method=$(this);
		$(this).css({'position':'absolute'});
		$("body select").css('visibility','hidden');
		center($(this),style);
		$("#screen").css({'display':'block','width':'100%','height':$(document).height()});
		$(this).css({'display':'block'});
		$(this).find("*[name='close']").click(function(){
			$("body select").css('visibility','visible');	
			$("#screen").hide();
			_method.hide();
			return false;
		})
	},

	//end 弹出框
	
	//浮动广告
	floatAd:function(){
		var _method=$(this);
		_method.animate({"top":$(window).scrollTop()+300},"slow");
		$(window).scroll(function(){
			_method.stop();
			_method.animate({"top":$(window).scrollTop()+300},"slow");
		})
		_method.find('.close').click(function(){
			$(this).closest("*[type='ad']").hide();
			return false;
		})
	},
	
	//手风琴效果
	accordion:function(_style){
		var _method=$(this),
			style=_style=='fold'?'fold':'normal';
		_method.children("dt").click(function(){
			var index=_method.children("dt").index($(this));
			if(style=='normal' && $(this).attr('class')=='on'){
				$(this).removeClass('on');
				_method.children('dd').eq(index).stop(true,true).css('display','none');
			}
			else{
				_method.children("dt").removeClass('on');
				$(this).addClass('on');
				_method.children('dd:not(eq('+index+'))').stop(true,true).css('display','none').eq(index).stop(true,true).css('display','block');
			}
			return false;
		})
	},
	
	//拖拽
	touchDrag:function(){
		$(this).each(function(){
			$(this).append('<p class="txt"></p><p class="nav"></p>').find('.pic').append($(this).find('.pic').html());
			var _method=$(this),_ul=_method.find('.pic'),_li=_ul.find('li'),_nav=_method.find('.nav'),_txt=_method.find('.txt'),
				width=_li.width(),length=_li.length,index=length/2,
				add_link=function(){
					if(autoPlay){clearInterval(autoPlay);}
					_ul.unbind().stop(true,true).animate({'left':-index*width},300,function(){
						typeof document.ontouchstart=='undefined'?_ul.bind('mousedown',dragPc):_ul.bind('touchstart',drag);
					});
					_nav.animate({'left':(index>=length/2?index-length/2:index)*_nav.width()},300);
					_txt.html('<a href="'+_li.eq(index).find('a').attr('href')+'">'+_li.eq(index).find('img').attr('alt')+'</a>').show();
					autoPlay=setInterval(function(){
						checkSite();
						index++;
						add_link();
					},3000);
				},
				checkSite=function(){
					if(index==0||index==length-1){
						index=Math.abs(index-length/2);
						_ul.css('left',-index*width);
					}
				},
				stopDefault=function(){return false;},
				drag=function(){
					checkSite();
					if(autoPlay){clearInterval(autoPlay);}
					var oldX=event.touches[0].pageX,oldY=event.touches[0].pageY,newX=0,newY=0,oldL=_ul.position().left,isMove=false,isMoveX=false;
					_ul.bind('touchmove',function(){
						newX=Math.min(Math.max(event.touches[0].pageX-oldX,-width),width);
						newY=event.touches[0].pageY-oldY;
						if(!isMove){
							isMove=true;
							isMoveX=Math.abs(newX)>Math.abs(newY);
							if(isMoveX){event.preventDefault();}
						}else{
							if(isMoveX){
								_ul.css('left',oldL+newX);
								oldY=event.touches[0].pageY;
								event.preventDefault();
							}
						}
					});
					_ul.bind('touchend',function(){
						if(!isMove){return false;}
						if(isMoveX&&Math.abs(newX)>10){
							index=Math.min(Math.max(newX>0?index-1:index+1,0),length-1);
							add_link();
						}else{
							autoPlay=setInterval(function(){
								checkSite();
								index++;
								add_link();
							},3000);
						}
					});
				},
				dragPc=function(e){
					checkSite();
					if(autoPlay){clearInterval(autoPlay);}
					var oldX=e.pageX,oldY=e.pageY,oldL=_ul.position().left,
						newX=0,newY=0;
					_li.find('a').unbind('click',stopDefault);
					$(document).bind('mousemove',function(e){
						newX=Math.min(Math.max(e.pageX-oldX,-width),width);
						_ul.css('left',oldL+newX);
						_li.find('a').bind('click',stopDefault).each(function(){
							this.ondragstart=function(){return false;};
						});
						window.getSelection?window.getSelection().removeAllRanges():document.selection.empty();
					});
					$(document).bind('mouseup',function(e){
						$(document).unbind('mousemove mouseup');
						if(Math.abs(newX)!=0){
							index=Math.min(Math.max(newX>0?index-1:index+1,0),length-1);
							add_link();
						}
					});
				},
				autoPlay=setInterval(function(){
						checkSite();
						index++;

						add_link();
				},3000);
			_nav.css('width',width/length*2).show(function(){add_link();});
			_ul.css({'width':width*length,'left':-width*length/2});
		});
		return this;
	},
	
	
	//鼠标悬停列表
	hoverList:function(){
		var _method=$(this);
		_method.each(function() {
			var hoverL=$(this).find("*[name='hoverList']");
			$(this).mouseover(function(){
				$(this).addClass('on').siblings().removeClass('on');
				hoverL.width()>200?hoverL.width(200):'';
				hoverL.stop(true,true).slideDown(300);
			})
			$(this).mouseleave(function(){
				$(this).removeClass('on');
				hoverL.stop(true,true).slideUp(300);
			})
            
        });
	},
	
	//星级评分
	grade:function(valObj,scoreObj){
		$(this).click(function(event){
			var event=event||window.event;
			var l=$(this).width();
			var score=Math.ceil((event.pageX-$(this).offset().left)/(2*l)*10)*2;
			$(this).find('em').width(score*10+'%');
			valObj.val(score);
			scoreObj.html(score*10);
		})
	},
	//弹出框
	jumpBox2:function(style){
		var _method=$(this);
		$(this).css({'position':'absolute'});
		//$("body select").css('visibility','hidden');//界面上的下拉框无缘无故的被隐藏了
		center($(this),style);
		$("#screen").css({'display':'block','width':'100%','height':$(document).height()});
		$(this).css({'display':'block'});
		$(this).find("*[name='close']").click(function(){
			//$("body select").css('visibility','visible');	
			$("#screen").hide();
			_method.hide();
			return false;
		})
	}
	//end 弹出框

	
	
});

//取中间值JS
	function center(obj,style) {		//取中间值
	var screenWidth = $(window).width(), screenHeight = $(window).height(); 
	var scrolltop = $(document).scrollTop();
	var objLeft = (screenWidth - obj.width())/2 ;
	var objTop = (screenHeight - obj.height())/2 + scrolltop;
	
	obj.css({position:'absolute',left: objLeft + 'px', top: objTop + 'px'/*,'display': 'block'*/});
	
	$(window).unbind('resize');
	$(window).bind('resize', function() { 
		center(obj,true);
	});
	
	$(window).unbind('scroll');
	if(true==style){
		$(window).bind('scroll', function() { 
			objTop = (screenHeight - obj.height())/2 + $(document).scrollTop();
			obj.css({top:objTop+'px'});
		});	
	}
};

//clear placeholder
;(function(f,h,$){var a='placeholder' in h.createElement('input'),d='placeholder' in h.createElement('textarea'),i=$.fn,c=$.valHooks,k,j;if(a&&d){j=i.placeholder=function(){return this};j.input=j.textarea=true}else{j=i.placeholder=function(){var l=this;l.filter((a?'textarea':':input')+'[placeholder]').not('.placeholder').bind({'focus.placeholder':b,'blur.placeholder':e}).data('placeholder-enabled',true).trigger('blur.placeholder');return l};j.input=a;j.textarea=d;k={get:function(m){var l=$(m);return l.data('placeholder-enabled')&&l.hasClass('placeholder')?'':m.value},set:function(m,n){var l=$(m);if(!l.data('placeholder-enabled')){return m.value=n}if(n==''){m.value=n;if(m!=h.activeElement){e.call(m)}}else{if(l.hasClass('placeholder')){b.call(m,true,n)||(m.value=n)}else{m.value=n}}return l}};a||(c.input=k);d||(c.textarea=k);$(function(){$(h).delegate('form','submit.placeholder',function(){var l=$('.placeholder',this).each(b);setTimeout(function(){l.each(e)},10)})});$(f).bind('beforeunload.placeholder',function(){$('.placeholder').each(function(){this.value=''})})}function g(m){var l={},n=/^jQuery\d+$/;$.each(m.attributes,function(p,o){if(o.specified&&!n.test(o.name)){l[o.name]=o.value}});return l}function b(m,n){var l=this,o=$(l);if(l.value==o.attr('placeholder')&&o.hasClass('placeholder')){if(o.data('placeholder-password')){o=o.hide().next().show().attr('id',o.removeAttr('id').data('placeholder-id'));if(m===true){return o[0].value=n}o.focus()}else{l.value='';o.removeClass('placeholder');l==h.activeElement&&l.select()}}}function e(){var q,l=this,p=$(l),m=p,o=this.id;if(l.value==''){if(l.type=='password'){if(!p.data('placeholder-textinput')){try{q=p.clone().attr({type:'text'})}catch(n){q=$('<input>').attr($.extend(g(this),{type:'text'}))}q.removeAttr('name').data({'placeholder-password':true,'placeholder-id':o}).bind('focus.placeholder',b);p.data({'placeholder-textinput':q,'placeholder-id':o}).before(q)}p=p.removeAttr('id').hide().prev().attr('id',o).show()}p.addClass('placeholder');p[0].value=p.attr('placeholder')}else{p.removeClass('placeholder')}}}(this,document,jQuery));

//operation on
$('input, textarea').placeholder();

//
$(".my_plate").click(function(){
$(".sec_list").toggle();
});
