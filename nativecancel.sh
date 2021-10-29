while true; do
   curl --location --request GET 'http://localhost:8080/v1/sample/native?status=cancel' &
   #sleep 0.001
   kill $(ps | grep 'curl' | head -n 1 | awk '{print $1}') &
   sleep 0.005
   curl --location --request GET 'http://localhost:8080/v1/sample/native?status=complete'
done


