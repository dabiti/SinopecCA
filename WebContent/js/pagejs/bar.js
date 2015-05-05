	function Submit_onclick() {
		if (parent.mainFrame.cols == "206,8,*") {
			parent.mainFrame.cols = "0,8,*"
			document.getElementById("ImgArrow").src = "images/ui/line_right.gif";
		} else {
			parent.mainFrame.cols = "206,8,*"
			document.getElementById("ImgArrow").src = "images/ui/line_left.gif";
		}
	}