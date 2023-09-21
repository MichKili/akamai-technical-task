#@formatter:off
export PATH=$PATH:/usr/local/go/bin:/root/go/bin

echo "SPANNER_EMULATOR_URL: " $SPANNER_EMULATOR_URL
DUMPFILE=/dump/dump.sql
DUMPFILE_GIFTCARDS=/dump/dump-giftcards.sql

echo "Create spanner db:  $SPANNER_PROJECT_ID > $SPANNER_INSTANCE_NAME > $SPANNER_DATABASE_NAME"
gcloud config configurations create emulator --quiet || true
gcloud config set auth/disable_credentials true
gcloud config set api_endpoint_overrides/spanner $SPANNER_EMULATOR_URL
gcloud spanner instances create $SPANNER_INSTANCE_NAME --config=emulator-config --description=Emulator --nodes=1 --project=$SPANNER_PROJECT_ID
gcloud spanner databases create $SPANNER_DATABASE_NAME --instance=$SPANNER_INSTANCE_NAME --project=$SPANNER_PROJECT_ID

echo "Create spanner gift cards db:  $SPANNER_PROJECT_ID_GIFTCARDS > $SPANNER_INSTANCE_NAME_GIFTCARDS > $SPANNER_DATABASE_NAME_GIFTCARDS"
gcloud spanner instances create $SPANNER_INSTANCE_NAME_GIFTCARDS --config=emulator-config --description=Emulator --nodes=1 --project=$SPANNER_PROJECT_ID_GIFTCARDS
gcloud spanner databases create $SPANNER_DATABASE_NAME_GIFTCARDS --instance=$SPANNER_INSTANCE_NAME_GIFTCARDS --project=$SPANNER_PROJECT_ID_GIFTCARDS

if [ -f $DUMPFILE ]; then
    echo "restoring spanner backup for:  $SPANNER_PROJECT_ID > $SPANNER_INSTANCE_NAME > $SPANNER_DATABASE_NAME"
    spanner-cli -p $SPANNER_PROJECT_ID -i $SPANNER_INSTANCE_NAME -d $SPANNER_DATABASE_NAME -f $DUMPFILE
    echo "database restored"
fi

if [ -f $DUMPFILE_GIFTCARDS ]; then
    echo "restoring spanner gift cards backup for:  $SPANNER_PROJECT_ID_GIFTCARDS > $SPANNER_INSTANCE_NAME_GIFTCARDS > $SPANNER_DATABASE_NAME_GIFTCARDS"
    spanner-cli -p $SPANNER_PROJECT_ID_GIFTCARDS -i $SPANNER_INSTANCE_NAME_GIFTCARDS -d $SPANNER_DATABASE_NAME_GIFTCARDS -f $DUMPFILE_GIFTCARDS
    echo "database for gift cards restored"
fi

echo "start backup scheduler with interval: $BACKUP_INTERVAL seconds"
while :; do
    sleep $BACKUP_INTERVAL
    echo "executing spanner backup"
    #we have to dump tables (data) in two steps, otherwise there will be no way to import them because of foreign key constraints
    spanner-dump -p $SPANNER_PROJECT_ID -i $SPANNER_INSTANCE_NAME -d $SPANNER_DATABASE_NAME --tables=$DUMP_TABLES_PRIMARY > $DUMPFILE.tmp
    spanner-dump -p $SPANNER_PROJECT_ID -i $SPANNER_INSTANCE_NAME -d $SPANNER_DATABASE_NAME --tables=$DUMP_TABLES_SECONDARY >> $DUMPFILE.tmp
    if [ "$?" -eq "0" ]; then
        # sometimes when container is stopping, error will occur and dump.sql will be empty
        mv $DUMPFILE.tmp $DUMPFILE
    fi


    echo "executing spanner backup for gift cards"
    spanner-dump -p $SPANNER_PROJECT_ID_GIFTCARDS -i $SPANNER_INSTANCE_NAME_GIFTCARDS -d $SPANNER_DATABASE_NAME_GIFTCARDS --tables=$DUMP_TABLES_GIFTCARDS_PRIMARY > $DUMPFILE_GIFTCARDS.tmp
    if [ "$?" -eq "0" ]; then
        mv $DUMPFILE_GIFTCARDS.tmp $DUMPFILE_GIFTCARDS
    fi

done
