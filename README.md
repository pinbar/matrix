To Do:
======
1. Match costCode instead of cost center name in the select menu on employee edit page. Wire up the GET correctly (right now its in fillDummyData)<br>
--Done do qa (fixed it client side)<br>
2. Cost center / client. Set inactive instead of delete (due to dependency on timesheet)
-- deleting the coscenter/client entity as of now 
<br>
3. Cleanup on the employee/ costocde 
4. validation dates etc employee-costcode use case 
5. Employee Client relationship.<br>
6. Encrypt password for DB storage.<br>
7. Attachment errors handling and messages.<br>

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
1. investigate hover on side bar (especially admin->group) <br>
2. look into this and other maven issues reported on jetty startup: <br>
   2013-04-19 23:14:09.498:INFO:oejs.Server:jetty-8.1.10.v20130312 
   2013-04-19 23:14:11.706:INFO:oejpw.PlusConfiguration:No Transaction manager found - if your webapp requires one, please configure one.
   2013-04-19 23:14:19.231:INFO:/matrix:No Spring WebApplicationInitializer types detected on classpath