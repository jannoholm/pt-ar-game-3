/// set proper message length in buffer
var buffer=argument[0];

// get the message size
var message_size = buffer_tell(buffer)-4;

// write it
buffer_seek(buffer, buffer_seek_start, 0);
buffer_write(buffer, buffer_s32, message_size);

// back to the end
buffer_seek(buffer, buffer_seek_start, message_size+4);