nombreBase=${1:?'Es necesario especificar el nombre de la Base'}
host=$2
hostDefecto=127.0.0.1
puerto=${3:?'Falta asignar el puerto'}
target=$4
targetDefecto=100
mkdir Experimentos_${nombreBase}
function ejecutarExperimentos(){
	for i in {1..4} 
	do
		if [[ $i == 1 ]]; then
		 	nombreWorkload=workloada
		elif [[ $i == 2 ]]; then
		 	nombreWorkload=workloadb
		elif [[ $i == 3 ]]; then
		 	nombreWorkload=workloadc
		elif [[ $i == 4 ]]; then
		 	nombreWorkload=workloadd
		 	
		fi
		mkdir Experimentos_${nombreBase}/workload_${i}
				for j in {1..10}
		 		do
		 			resultado=$((${target:=$targetDefecto}*$j))
					cadena1=$nombreWorkload
		 		  	mkdir Experimentos_${nombreBase}/workload_${i}/Throughput_${resultado}
		 			for k in {1..6} 
		 			 do
						./bin/ycsb load $nombreBase -s -P workloads/$nombreWorkload -p "${host:=$hostDefecto}" -p "$puerto"  -p  operationcount=10000  -target $resultado>workload_${i}/Throughput_${resultado}/LCopia${k}.txt 
						./bin/ycsb run $nombreBase -s -P workloads/$nombreWorkload -p "${host:=$hostDefecto}" -p "$puerto"  -p  operationcount=10000  -target $resultado>workload_${i}/Throughput_${resultado}/RCopia${k}.txt 
		 			done
				done
	done
}

ejecutarExperimentos "$@"
