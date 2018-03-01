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


// connect to mysql atabase
$dsn = 'mysql:dbname=RestauranteA;host=127.0.0.1;charset=utf8';
try {
    $dbh = new PDO($dsn, 'root', 'root');
} catch (PDOException $e) {
    die('Connection failed: ');
}

// defining routes
// first route - this is an example - you can here define the API
// http://rest1.test/nome
$app->get('/nome', function () use ($app, $dbh) {
    return new Response('Obrigada por visitar!', 200);
});


// Mostra todas as Ementas
// ex :  http://rest1.test//allEmentas
$app->match('/allEmentas', function () use ($app, $dbh) {
    $sth = $dbh->prepare('SELECT id_Ementa, entrada, pratoCarne, pratoPeixe, sobremesa, precos FROM Ementa');
   	$sth->execute(array($id_Ementa));

    $ementa = $sth->fetchAll(PDO::FETCH_ASSOC);
    return $app->json($ementa);
})
->method('GET|POST');

// Mostra ementa pelo ID 
// ex : http://rest1.test/Ementa/
$app->match('/Ementa/{id_Ementa}', function ($id_Ementa) use ($app, $dbh) {
    $sth = $dbh->prepare('SELECT id_Ementa, entrada, pratoCarne, pratoPeixe, sobremesa, precos FROM Ementa WHERE id_Ementa=?');
    $sth->execute(array($id_Ementa));

    $ementa = $sth->fetchAll(PDO::FETCH_ASSOC);
    if(empty($ementa)) {
        return $app->json(array("Result"=>"Não existe ementa com o id $id_Ementa"));
    }

    return $app->json($ementa);
})
->method('GET|POST') 
->value("id_Ementa", 1) 
->assert('id_Ementa', '\d+'); 


// Mostra todos os Restaurantes
// ex : http://rest1.test/
$app->match('/allRestaurante', function () use ($app, $dbh) {
    $sth = $dbh->prepare('SELECT * FROM Restaurante');
    $sth->execute(array($id_Restaurante));

    $restaurante = $sth->fetchAll(PDO::FETCH_ASSOC);
    return $app->json($restaurante);
})
->method('GET|POST');


// Mostra restaurnte pelo ID 
// ex : http://rest1.test/Restaurante/
$app->match('/Restaurante/{id_Restaurante}', function ($id_Restaurante) use ($app, $dbh) {
    $sth = $dbh->prepare('SELECT * FROM Restaurante WHERE id_Restaurante=?');
    $sth->execute(array($id_Restaurante));

    $restaurante = $sth->fetchAll(PDO::FETCH_ASSOC);
    if(empty($restaurante)) {
        return $app->json(array("Result"=>"Não existe restaurante com o id  $id_Restaurante"));
    }

    return $app->json($restaurante);
})
->method('GET|POST') 
->value("id_Restaurante", 1) 
->assert('id_Restaurante', '\d+');


// Pesquisa por Prato de Carne
// ex : http://rest1.test/searchPratoCarne/
$app->match('/searchPratoCarne/{pratoCarne}', function ($pratoCarne) use ($app, $dbh) {
    $sth = $dbh->prepare('SELECT id_Ementa, pratoCarne, precos FROM Ementa WHERE pratoCarne = ? ');
    $sth->execute(array($pratoCarne));

    $ementa = $sth->fetchAll(PDO::FETCH_ASSOC);
    if(empty($ementa)) {
        return $app->json(array("Result"=>"Não existe Prato de Carne $pratoCarne na ementa."));
    }

    return $app->json($ementa);
})
->method('GET|POST'); 

// Pesquisa por Prato de Peixe
// ex : http://rest1.test/searchPratoPeixe/
$app->match('/searchPratoPeixe/{pratoPeixe}', function ($pratoPeixe) use ($app, $dbh) {
    $sth = $dbh->prepare('SELECT id_Ementa, pratoPeixe, precos FROM Ementa WHERE pratoPeixe = ? ');
    $sth->execute(array($pratoPeixe));

    $ementa = $sth->fetchAll(PDO::FETCH_ASSOC);
    if(empty($ementa)) {
        return $app->json(array("Result"=>"Não existe Prato de Peixe $pratoPeixe  na ementa."));
    }

    return $app->json($ementa);
})
->method('GET|POST'); 

// Pesquisa por Preco de Ementa 
// ex : http://rest1.test/searchPrecos/
$app->match('/searchPrecos/{precos}', function ($precos) use ($app, $dbh) {
    $sth = $dbh->prepare('SELECT * FROM Ementa WHERE precos = ? ');
    $sth->execute(array($precos));

    $ementa = $sth->fetchAll(PDO::FETCH_ASSOC);
    if(empty($ementa)) {
        return $app->json(array("Result"=>"Não existe Ementa de  $precos ."));
    }

    return $app->json($ementa);
})
->method('GET|POST'); 


// Pesquisa por Restaurante por localização
// ex : http://rest1.test/searchRestauranteLocalizacao/
$app->match('/searchRestauranteLocalizacao/{localizacao}', function ($localizacao) use ($app, $dbh) {
    $sth = $dbh->prepare('SELECT localizacao, rating, horario, ementa, nome, lotacao, precoMedio FROM Restaurante WHERE localizacao = ? ');
    $sth->execute(array($localizacao));

    $restaurante = $sth->fetchAll(PDO::FETCH_ASSOC);
    if(empty($restaurante)) {
        return $app->json(array("Result"=>"Não existe restaurante na zona $localizacao ."));
    }

    return $app->json($restaurante);
})
->method('GET|POST');

// Pesquisa por Restaurante por lotacao
// ex : http://rest1.test/searchRestauranteLotacao/
$app->match('/searchRestauranteLotacao/{lotacao}', function ($lotacao) use ($app, $dbh) {
    $sth = $dbh->prepare('SELECT localizacao, rating, horario, nome, lotacao, precoMedio FROM Restaurante WHERE lotacao = ? ');
    $sth->execute(array($lotacao));

    $restaurante = $sth->fetchAll(PDO::FETCH_ASSOC);
    if(empty($restaurante)) {
        return $app->json(array("Result"=>"Não existe restaurante com lotacao de  $lotacao ."));
    }

    return $app->json($restaurante);
})
->method('GET|POST');




// Editar Ementa
// { "entrada": "Aperitivos a Maginas", "pratoCarne": "Francesinha de Aves", "pratoPeixe": "Francesinha do Mar", "sobremesa": "Mousse Chocolate", "precos": "15 euros" }
//ex :  http://rest1.test/editEmenta/
$app->put('/editEmenta/{id_Ementa}', function(Request $request, $id_Ementa) use ($app, $dbh) {
    $data = json_decode($request->getContent(), true);
    
    $sth=$dbh->prepare(' UPDATE Ementa SET entrada=? , pratoCarne=? , pratoPeixe=?, sobremesa=?, precos=?  WHERE id_Ementa=? ');
    
    $sth->execute(array($data['entrada'],$data['pratoCarne'],$data['pratoPeixe'],$data['sobremesa'],$data['precos'],$id_Ementa));
   
    return $app->json($data, 200);
    
})->assert('id_Ementa', '\d+');


// Editar Restaurante 
// {"localizacao" : "", "rating" : "", "horario" : "", "ementa" : "", "nome" : "", "lotacao" : "", "precoMedio" : ""}
//ex :  http://rest1.test/editRestaurante/
$app->put('/editRestaurante/{id_Restaurante}', function(Request $request, $id_Restaurante) use ($app, $dbh) {
    $data = json_decode($request->getContent(), true);
    
    $sth=$dbh->prepare(' UPDATE Restaurante SET localizacao=?, rating=?, horario=?, ementa=?, nome=?, lotacao=?, precoMedio=?  WHERE id_Restaurante=? ');
    
    $sth->execute(array($data['localizacao'],$data['rating'],$data['horario'],$data['ementa'],$data['nome'],$data['lotacao'],$data['precoMedio'],$id_Restaurante));
   
    return $app->json($data, 200);
    
})->assert('id_Restaurante', '\d+');  


// Apagar Restaurante 
// exp : http://rest1.test/delRestaurante/
$app->delete('/delRestaurante/{id_Restaurante}', function($id_Restaurante) use ($app, $dbh) {

    $sth=$dbh->prepare('DELETE from Restaurante WHERE id_Restaurante = ?');
    $sth->execute(array($id_Restaurante));
    $restaurante = $sth->fetchAll(PDO::FETCH_ASSOC);

    if($restaurante < 1) {
        return new Response("Não existe restaurante com o id $id_Restaurante", 404);
    }
    
    return new Response(null, 204);
})
->assert('id_Restaurante', '\d+');  

// Apagar Ementa 
// exp : http://rest1.test/delEmenta/
$app->delete('/delEmenta/{id_Ementa}', function($id_Ementa) use ($app, $dbh) {

    $sth=$dbh->prepare('DELETE from Ementa WHERE id_Ementa = ?');
    $sth->execute(array($id_Ementa));
    $ementa = $sth->fetchAll(PDO::FETCH_ASSOC);

    if($ementa < 1) {
        return new Response("Não existe ementa com o id $id_Ementa", 404);
    }
    
    return new Response(null, 204);
})
->assert('id_Ementa', '\d+'); 


/*
// e.g., curl -X GET http://api.dev/books/xml/1 OR curl -X GET http://api.dev/books/json
$app->get("/books/{_format}/{offset}", function (Request $request, $offset) use ($app, $dbh) {
    $sth = $dbh->prepare('SELECT id, title, author, isbn FROM books LIMIT 10 OFFSET :offset');
    $sth->bindValue(":offset", (int) $offset , PDO::PARAM_INT);
    
    $sth->execute();
    $books = $sth->fetchAll(PDO::FETCH_ASSOC);

    $format = $request->getRequestFormat();
    return new Response($app['serializer']->serialize($books, $format), 200, array(
        "Content-Type" => $request->getMimeType($format)
    ));
})
->value("_format", "xml")
->assert("_format", "xml|json") // check if format is XML or JSON
->value("offset", 0) // define the default value for offset
->assert("offset", "\d+"); // verify that id is a digit

// e.g., curl -X GET -i http://api.dev/getbooks/2/5
$app->get('/getbooks/{offset}/{limit}', function ($offset, $limit) use ($app, $dbh) {
    // get all the books
    $sth = $dbh->prepare('SELECT * FROM books LIMIT :limit OFFSET :offset');
    $sth->bindValue(":offset", (int) $offset , PDO::PARAM_INT);
    $sth->bindValue(":limit", (int) $limit , PDO::PARAM_INT);
    $sth->execute();
    $livros = $sth->fetchAll(PDO::FETCH_ASSOC);
    
    return $app->json($livros);
})
->value("offset",0)
->value("limit",10)
->assert('offset', '\d+')
->assert('limit', '\d+');



// Inserir ementa 
// e.g., curl -X POST -H "Content-Type: application/json" -d '{"entrada":" ","pratoCarne":" ","pratoPeixe":" ", "sobremesa":" ", "precos": " "}' 
// http://rest1.test/insertEmenta
$app->post('/insertEmenta', function(Request $request) use ($app, $dbh) {
    $data = json_decode($request->getContent(), true); 

    $sth = $dbh->prepare('INSERT INTO Ementa (entrada, pratoCarne, pratoPeixe, sobremesa, precos)
            VALUES(:entrada, :pratoCarne, :pratoPeixe, :sobremesa, :precos)');

    $sth->execute($data);
    $id_Ementa = $dbh->lastInsertId();

  
    $response = new Response('Enserido com sucesso', 201);
    $response->headers->set('Location', "/insertEmenta/$id_Ementa");
    return $response;
});

*/


        // enable debug mode - optional this could be commented
        $app['debug'] = true;
        // execute the app
        $app->run();
