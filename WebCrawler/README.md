#A (simple) Web Crawler in Java

The objective is to make a Web Crawler that gets the content of a link and stores it's body in a text file. Then it stores different links that the original one makes reference in a different text file. In the end, it leaves a single text file that contains all the info it got previously organized.

Made using the Eclipse IDE in Java 1.8.0.

Tutorial : http://www.vogella.com/tutorials/RSSFeed/article.html

---------------------------------------------------------------------------
## Known issues:

- If a section contain symbols considered not character by **asCharacters** method, the string returned may stop at the first symbol read. Leaving the description of the section inside the table incomplete.

- The way I "corrected" the link-get problem only works if the actual link for the article is put first before any other link section. If that's not the case, it may get a different link that is not from the article.

- Some of the problems corrected are connected to the fact that the way the XML files were built may vary from RSS Feed to RSS Feed. This project doesn't guarantee that it will be able to read well **EVERY** feed put inside the list by the user.
