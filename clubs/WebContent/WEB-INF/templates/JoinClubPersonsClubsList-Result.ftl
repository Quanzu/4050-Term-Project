<html>
<head><title>Join Club</title>
</head>
<body>

<h3>Joining a club</h3>

<form method=post action="JoinClub">

<p>Select a person: 
<select
  name="person_id" size="1">
  <#list persons as person>
  <option value=${person[0]}>${person[1]} ${person[2]}</option>
  </#list>
</select>

<p>Select a club: 
<select
  name="club_name" size="1">
  <#list clubs as club>
  <option>${club}</option>
  </#list>
</select>

<p><input type=submit> <input type=reset>

</form>

<p><p>Back to the <a href="ShowMainWindow"> main window</a>

</body>
</html>
