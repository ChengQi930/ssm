
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> <head> <meta http-equiv="Content-Type"
	content="text/html; charset=utf-8"> <title>文件上传下载</title></head> <body>



<form action="GetFileContent" method="post"
	enctype="multipart/form-data"> 选择文件:<input type="file"
	name="accountFile" width="120px"> <input type="submit"
	value="获得文件内容"></form> <hr> <form action="UpdateAssetAccount"
	method="post" enctype="multipart/form-data"> 选择文件:<input
	type="file" name="accountFile" width="120px">
</br> <input type="text" name="accountType" width="120px" value="2">
<input type="submit" value="更新台账"></form></body> </html>