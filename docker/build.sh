#!/bin/bash

. ./docker/kubernetes/build_mysql.sh

. ./docker/build_base.sh
. ./docker/build_project.sh
. ./docker/build_glial.sh
