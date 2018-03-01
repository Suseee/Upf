<?php
require_once __DIR__ . '/../vendor/autoload.php';

// namespaces
use Silex\Application;
use Silex\Provider\SerializerServiceProvider;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;


// create the app
$app = new Silex\Application();

// using for serialize data for xml and json format
$app->register(new SerializerServiceProvider());


// Conexão a base de dados 
$dsn = 'mysql:dbname=webService2;host=127.0.0.1;charset=utf8';
try {
    $dbh = new PDO($dsn, 'root', 'root');
} catch (PDOException $e) {
    die('Connection failed: ');
}

// Retorna todos os restaurantes 
// http://ws.test/allRestaurante
$app->get('/allRestaurante', function () use ($app, $dbh) {
    
    $array = array();
    $sth = $dbh->prepare('SELECT url FROM url');
    $sth->execute(array());
    $urls = $sth -> fetchAll(PDO::FETCH_ASSOC);

    // percorre todos os urls 
    foreach ($urls as $url) {
    	$url['url'] .= "allRestaurante";
    	//return $app->json(array("Result"=>" $url "));
    	$json = file_get_contents($url['url']);
    	$data = json_decode($json); // descodifica o codigo
    	$array[] = $data;
    }

    return $app -> json($array);

});


// enable debug mode - optional this could be commented
$app['debug'] = true;
// execute the app
$app->run();
