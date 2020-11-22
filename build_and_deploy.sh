. ./build.sh

cd docker/kubernetes/

. ./tag_and_push.sh
. ./deploy.sh

kubectl rollout restart deployment/jeldwen