
To Do:
======
1. UI change from drop-down to "feeder" for employee/ costocde <br>
2. Validation dates etc employee-costcode use case <br>
3. Employee Client relationship.<br>
4. Encrypt password for DB storage.<br>
5. Attachment errors handling and messages.<br>

Features:
=========
1. Submit timesheet : workflow  <br>
2. PTO/Vacation and align with timesheet <br>
3. Reports (individual , grouped) <br>
4. Mail / print /export <br>
5. Employee domain edit to add type / status <br>
6. Cost center vs client and its relationship to employee <br>
7. Notice board (blog style?) <br>
8. Job Postings <br>

Validations
===========
1. Timesheet: Basic validation implemented. Attachments section validation needed.

Nice to have:
=============
2. look into this and other maven issues reported on jetty startup: <br>
   2013-04-19 23:14:09.498:INFO:oejs.Server:jetty-8.1.10.v20130312 
   2013-04-19 23:14:11.706:INFO:oejpw.PlusConfiguration:No Transaction manager found - if your webapp requires one, please configure one.
   2013-04-19 23:14:19.231:INFO:/matrix:No Spring WebApplicationInitializer types detected on classpath