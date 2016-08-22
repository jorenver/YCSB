nombreBase=${1:?'Es necesario especificar el nombre de la Base'}
host=${2:?'Se debe saber cual workload necesito procesar?'}
hostDefecto=127.0.0.1
puerto=${3:?'Indique un puerto'}
target=$4
targetDefecto=1000
array_workload=("workloada" "workloadb" "workloadc" "workloadd")

if [ ! -d experimento5 ]; then 
     mkdir experimento5   
fi

function ejecutarExperimento5(){
        for j in {0..3}
                do
                        nombreWorkload=${array_workload[$j]}
			for i in {1..10}
		 		do
					resultado=$((${target:=$targetDefecto}*$i))
					./bin/ycsb run $nombreBase -s -P workloads/$nombreWorkload -p "${host:=$hostDefecto}" -p "$puerto" -target 5000 -p operationcount=10000 -p recordcount=$resultado > experimento5/${nombreWorkload}_Cantidad_Registro$resultado.txt
                                done
                 done
}
ejecutarExperimento5 "$@"
  
