nombreBase=${1:?'Es necesario especificar el nombre de la Base'}
host=${2:?'Se debe saber cual workload necesito procesar?'}
hostDefecto=127.0.0.1
puerto=${3:?'Indique un puerto'}
target=$4
targetDefecto=1000
array_workload=("workloada" "workloadb" "workloadc" "workloadd")

if [ ! -d experimento6 ]; then 
     mkdir experimento6   
fi

function ejecutarExperimento6(){
        for j in {0..3}
                do
                         nombreWorkload=${array_workload[$j]}
		         for i in {1..10}
			         do
		  		      resultado=$((${i}*2)) 	                   
			              ./bin/ycsb run $nombreBase -s -P workloads/$nombreWorkload -p "${host:=$hostDefecto}" -p "$puerto"   -p  operationcount=10000  -p  recordcount=1000 -target 1000 -threads $resultado  > experimento6/${nombreWorkload}_Clientes$resultado.txt 
			        done
                 done
}
ejecutarExperimento6 "$@"
  
