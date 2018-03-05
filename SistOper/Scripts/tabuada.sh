
#	!/bin/bash
#	if [ -z $1 ];
#	then
#		echo "Usage: " $0 "[PARAM]"
#		exit	
#	fi


	echo -n "Introduza o nr da tabuada: "
	read nr

	if [[ $nr =~ [^[:digit:]] ]]
	then
		echo $nr "Is not a number, usage: [0-9]"
   	 exit	
	fi

	for number in {1..10}
	do
		let res=nr*number
		echo $nr " * " $number " = " $res
	done




