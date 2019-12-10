//http://kamranahmed.info/toast
//套件 jquery.toast
function myToast(msg) {
	$.toast({
		text: msg,
		hideAfter: 5000,
		position: {
			right: 20,
			top: 110
	    },
	    textAlign: 'center', 
		stack: false,
		loader: false,
	});
}

function myToastError(errMsg) {
	$.toast({
		text: errMsg,
		hideAfter: 5000,
		position: {
			right: 20,
			top: 110
	    },
	    textAlign: 'center', 
		stack: false,
		loader: false,
		textColor: 'red',
	});
}

function myToastDeposit(msg) {
	$.toast({
		text: msg,
		hideAfter: 3000,
		position: {
			right: 850,
			top: 175
	    },
	    textAlign: 'center', 
		stack: false,
		loader: false,
	});
}

//$.toast.options = {
//        text: '',
//        heading: '',
//        showHideTransition: 'fade',
//        allowToastClose: true,
//        hideAfter: 3000,
//        loader: true,
//        loaderBg: '#9EC600',
//        stack: 5,
//        position: 'bottom-left',
//        bgColor: false,
//        textColor: false,
//        textAlign: 'left',
//        icon: false,
//        beforeShow: function () {},
//        afterShown: function () {},
//        beforeHide: function () {},
//        afterHidden: function () {},
//        onClick: function () {}
//    };