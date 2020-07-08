jQuery.fn.rotate = function(angle,whence) {
	var _this = this;
	var p = this.get(0);

	// we store the angle inside the image tag for persistence
	if (!whence) {
		p.angle = ((p.angle==undefined?0:p.angle) + angle) % 360;
	} else {
		p.angle = angle;
	}

	if (p.angle >= 0) {
		var rotation = Math.PI * p.angle / 180;
	} else {
		var rotation = Math.PI * (360+p.angle) / 180;
	}
	var costheta = Math.cos(rotation);
	var sintheta = Math.sin(rotation);

	if (document.all && !window.opera) {
		var canvas = document.createElement('img');

		canvas.src = p.src;
		canvas.height = p.height;
		canvas.width = p.width;

		canvas.style.filter = "progid:DXImageTransform.Microsoft.Matrix(M11="+costheta+",M12="+(-sintheta)+",M21="+sintheta+",M22="+costheta+",SizingMethod='auto expand')";
	} else {
		var canvas = document.createElement('canvas');
		if (!p.oImage) {
			canvas.oImage = new Image();
			canvas.oImage.src = p.src;
		} else {
			canvas.oImage = p.oImage;
		}
		
		var imageWidth = canvas.oImage.width==0?_this.width():canvas.oImage.width;
		var imageHeight = canvas.oImage.height==0?_this.height():canvas.oImage.height;
		canvas.style.width = canvas.width = Math.abs(costheta*imageWidth) + Math.abs(sintheta*imageHeight);
		canvas.style.height = canvas.height = Math.abs(costheta*imageHeight) + Math.abs(sintheta*imageWidth);

		var context = canvas.getContext('2d');
		context.save();
		if (rotation <= Math.PI/2) {
			context.translate(sintheta*imageHeight,0);
		} else if (rotation <= Math.PI) {
			context.translate(canvas.width,-costheta*imageHeight);
		} else if (rotation <= 1.5*Math.PI) {
			context.translate(-costheta*imageWidth,canvas.height);
		} else {
			context.translate(0,-sintheta*imageWidth);
		}
		context.rotate(rotation);
		context.drawImage(canvas.oImage, 0, 0, imageWidth, imageHeight);
		context.restore();
	}
	canvas.id = p.id;
	canvas.angle = p.angle;
	p.parentNode.replaceChild(canvas, p);
}

jQuery.fn.rotateRight = function(angle) {
	this.rotate(angle==undefined?90:angle);
}

jQuery.fn.rotateLeft = function(angle) {
	this.rotate(angle==undefined?-90:-angle);
}

/*****************相关调用**************************/
function zoomIn(sid,lid,largeUrl){
	$("#"+sid).hide();
	$("#"+lid).show();
	if($("#"+lid+"_img").attr("src")==null
			||$("#"+lid+"_img").attr("src")==""){
		$("#"+lid+"_img").attr("src",largeUrl);
	}
}
/**
 * 缩小
 * @param sid 小图标签ID
 * @param lid 大图标签ID
 */
function zoomOut(sid,lid){
	$("#"+sid).show();
	$("#"+lid).hide();
}