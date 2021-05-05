## Set up Sharding using Docker Containers

### Config servers
Start config servers (3 member replica set)
```
docker-compose -f config-server/docker-compose.yaml up -d
```
Initiate replica set
```
mongo mongodb://192.168.1.5:40001
```
```
rs.initiate(
  {
    _id: "cfgrs",
    configsvr: true,
    members: [
      { _id : 0, host : "192.168.1.5:40001" },
      { _id : 1, host : "192.168.1.5:40002" },
      { _id : 2, host : "192.168.1.5:40003" }
    ]
  }
)

rs.status()
```

### Shard 1 servers
Start shard 1 servers (3 member replicas set)
```
docker-compose -f irelandShard/docker-compose.yaml up -d
```
Initiate replica set
```
mongo mongodb://192.168.1.5:50001
```
```
rs.initiate(
  {
    _id: "irelandShardRs",
    members: [
      { _id : 0, host : "192.168.1.5:50001" },
      { _id : 1, host : "192.168.1.5:50002" },
      { _id : 2, host : "192.168.1.5:50003" }
    ]
  }
)

rs.status()
```

### Mongos Router
Start mongos query router
```
docker-compose -f mongos/docker-compose.yaml up -d
```

### Add shard to the cluster
Connect to mongos
```
mongo mongodb://192.168.1.5:60000
```
Add shard
```
sh.addShard("irelandShardRs/192.168.1.5:50001,192.168.1.5:50002,192.168.1.5:50003")
sh.status()
```
## Adding another shard
### Shard 2 servers
Start shard 2 servers (3 member replicas set)
```
docker-compose -f indiaShard/docker-compose.yaml up -d
```
Initiate replica set
```
mongo mongodb://192.168.1.5:50004
```
```
rs.initiate(
  {
    _id: "indiaShardRs",
    members: [
      { _id : 0, host : "192.168.1.5:50004" },
      { _id : 1, host : "192.168.1.5:50005" },
      { _id : 2, host : "192.168.1.5:50006" }
    ]
  }
)

rs.status()
```
### Add shard to the cluster
Connect to mongos
```
mongo mongodb://192.168.1.5:60000
```
Add shard
```
sh.addShard("indiaShardRs/192.168.1.5:50004,192.168.1.5:50005,192.168.1.5:50006")
sh.status()
```
show


#to check containers
docker ps

#enable sharding for a database
sh.enableSharding("dbname")

#sharding a collection within that database using shard key as hashed booking_id
sh.shardCollection("dbname.bookings", {"booking_id":"hashed"})

#get shard distribution
db.bookings.getShardDistribution()

