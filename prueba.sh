file="/experimento"
if [ ! -d experimento ]; then 
     mkdir experimento   
fi
./bin/ycsb run redis -s -P workloads/workloada -p "redis.host=127.0.0.1" -p "redis.port=6379" -target 5000 -p operationcount=10000 -p recordcount=1000 > experimento/Cantidad_Registro.txt
#mv Cantidad_Registro.txt  experimento