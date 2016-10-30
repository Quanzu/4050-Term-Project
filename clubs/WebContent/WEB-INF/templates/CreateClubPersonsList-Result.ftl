<html>
<head><title>Join Club</title>
</head>
<body>

<h3>Joining a club</h3>

<form method=post action="CreateClub">

<p>Enter club name: <input name="club_name" type=text size="20">

<p>Enter club address: <input name="club_address" type=text size="50">

<p>Select a founder: 
<select
  name="founder_id" size="1">
  <#list persons as person>
  <option value=${person[0]}>${person[1]} ${person[2]}</option>
  </#list>
</select>

<p><input type=submit> <input type=reset>

</form>

<p><p>Back to the <a href="ShowMainWindow"> main window</a>

</body>
</html>
