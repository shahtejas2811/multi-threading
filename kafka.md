#Apache Kafka: 
	Message durability provided by Apache Kafka as following semantics
	1. At least once
		- Producer send message to Kafka broker and leader broker commit a message send back ACK to producer
		- Consider ACK is not received by prouder due to network error. Then producer again send message to Kafka broker
		- Kafka broker can store message again as there no mechanism to check duplicate message
		- By default semantics
	
	2. At most once
		- If we configure retry=0 then producer send message only once. So there is no duplication 
		- But we can lose message

	3. Exactly once:	
		- some use cases want to implement exactly-once semantics i.e no message loss and no duplication.
		- Kafka offers an idempotent producer configuration. set enable.idempotence = true.
    	- At Producer side:
			a. It will perform an initial handshake with the leader broker
			b. Ask for a unique producer id.
		- At Broker side:
			a. The broker dynamically assigns a unique ID to each producer.
        	b. The producer API will start assigning a sequence number to each message start with zero and monotonically increments per partition.
			c. when the I/O thread sends a message to a leader, the message is uniquely identified by the producer id and a sequence number.
		  		 Now, the broker knows that the last committed message sequence number is X,
		 		 and the next expected message sequence number is X+1.
		  		This allows the broker to identify duplicates as well as missing sequence numbers.
