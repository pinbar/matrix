To Do:
======
Attachment errors handling and messages.
Hidden password field.[Done in forms, needed in Data List]
Encrypt password for DB storage. 
Change password by self.
Authentication failure error message. [DONE]
Remember me. [Done]

Features:
=========
Submit timesheet : workflow  
PTO/Vacation and align with timesheet
Reports (individual , grouped) 
Mail / print /export 
Employee domain edit to add type / status
Cost center vs client and its relationship to employee
Notice board (blog style?)
Job Postings

Validations
===========
Timesheet: Basic validation implemented. Attachments section validation needed.

Nice to have:
=============
investigate hover on side bar (especially admin->group) 
look into this and other maven issues reported on jetty startup:
2013-04-19 23:14:09.498:INFO:oejs.Server:jetty-8.1.10.v20130312
2013-04-19 23:14:11.706:INFO:oejpw.PlusConfiguration:No Transaction manager found - if your w
ebapp requires one, please configure one.
2013-04-19 23:14:19.231:INFO:/matrix:No Spring WebApplicationInitializer types detected on cl
asspath