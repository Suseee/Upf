#!/bin/bash
	
	
	echo -n "Introduza 1 nr: "
	read nr1
	
	if [[ $nr1 =~ [^[:digit:]] ]]
	then
		echo $nr1 "não é um número."
		echo "Introduza novamente: "
		read nr1
    	exit	

	echo -n "Introduza a operacao '- + * /' : "
	read operacao

	echo -n "Introduza 2 nr: " 
	read nr2

	if [[ $nr2 =~ [^[:digit:]] ]]
	then
		echo $nr2 "não é um número."
    	exit	
	fi

	echo -n "Resultado: "
	echo "$nr1$operacao$nr2" | bc
