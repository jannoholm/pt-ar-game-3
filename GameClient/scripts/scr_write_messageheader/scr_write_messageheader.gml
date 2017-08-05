/// write message header to buffer
var buffer = argument[0];
var message_type = argument[1];
var message_id = argument[2];
var client_id = argument[3];

// reset buffer just in case
buffer_seek(buffer, buffer_seek_start, 0);

// write necessary headers
buffer_write(buffer, buffer_s32, 0); // packet size, will be rewritten
buffer_write(buffer, buffer_s32, 0); // header size, will be rewritten
buffer_write(buffer, buffer_s32, message_type); // message type
buffer_write(buffer, buffer_u64, message_id); // message id
buffer_write(buffer, buffer_string, client_id); // client id

// set proper message header size
var header_size = buffer_tell(buffer)-8;
buffer_seek(buffer, buffer_seek_start, 4);
buffer_write(buffer, buffer_s32, header_size); 

// back to the end
buffer_seek(buffer, buffer_seek_start, header_size+8);