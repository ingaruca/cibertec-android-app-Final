<?php

$cn = new PDO("mysql:host=localhost:3306; dbname=bd_android_final", "root", "mysql");

$res = $cn->query("SELECT * FROM Usuario");

$datos = array();

foreach ($res as $row) {
    $datos[] = $row;
}

echo json_encode($datos);

 ?>
