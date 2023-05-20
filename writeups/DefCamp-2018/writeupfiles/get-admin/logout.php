<?php

include_once('config.php');
session_destroy();
setcookie('user','',time()-10);
header("Location: /index.php");