<html>
<body>
<h4>Добавить сообщение</h4>
<form method="post" action="/new_msg">
    <input type="text" class="form-control" name="uid" placeholder="uid">
    <input type="text" class="form-control" name="text" placeholder="text">
    <button class="btn btn-dark" type="submit">Сохранить сообщение</button>
</form>
<h4>Изменить имя</h4>
<form method="post" action="/new_name">
    <input type="text" class="form-control" name="uid" placeholder="uid">
    <input type="text" class="form-control" name="name" placeholder="name">
    <button class="btn btn-dark" type="submit">Изменить</button>
</form>

</body>
</html>