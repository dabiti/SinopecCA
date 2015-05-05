function onChange(i) {
	childSort = document.all("child" + i);
	obj_image = document.all("image" + i);
	if (childSort.style.display == "none") {
		childSort.style.display = "";
		document.getElementById("image" + i).src = "images/ui/icon_open.png";
	} else {
		childSort.style.display = "none";
		document.getElementById("image" + i).src = "images/ui/icon_close.png";
	}
}