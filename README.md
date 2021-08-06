# DataX-Hitocas

集群模式不能开启的bug.
eg：content 里可添加多个任务，core.container.model:"taskGroup" 必填
开启集群模式后MySQL,SqlServer,Oracle等一些reader ,writer 有些解析没有写，导致不能运行（部分已解决），自定义了一个Transformer(写的不太好，待优化)

{
	"job": {
		"content": [{
			"reader": {
				"name": "mysqlreader",
				"parameter": {
					"table": "t_dict",
					"column": ["create_time", "description", "dict_key", "id"],
					"jdbcUrl": "jdbc:mysql://192.168.30.68:3306/dataplatform",
					"password": "hitocas",
					"username": "root",
					"fetchSize": 1
				},
				"dataSourceId": "78"
			},
			"writer": {
				"name": "mysqlwriter",
				"parameter": {
					"table": "t_dict",
					"column": ["create_time", "description", "dict_key", "id"],
					"jdbcUrl": "jdbc:mysql://192.168.30.68:3306/writer",
					"password": "hitocas",
					"username": "root",
					"writeMode": "replace",
					"writeCompareField": []
				},
				"usedTableId": 44,
				"dataSourceId": "79"
			}
		}],
		"setting": {
			"speed": {
				"channel": "1"
			}
		}
	},
	"core": {
		"container": {
			"model": "taskGroup",
			"taskGroup": {
				"id": 245123973
			}
		}
	}
}
