function autoPicSize(sender, maxW, maxH) {
	var x = sender.width;
	var y = sender.height;
	if ((x * maxH) >= (y * maxW))
		sender.width = maxW;
	else
		sender.height = maxH;
}