#A (simple) RSS Reader in Java

The objective is to make a RSS Reader that stores RSS URL's localy and, through them, pulling feeds and showing the URL's contained in them. For each URL contained, shows a descripion and makes possible visiting each one of them. A RSS Reader, indeed.

Made using the Eclipse IDE.

Tutorial : http://www.vogella.com/tutorials/RSSFeed/article.html

---------------------------------------------------------------------------
## Known issues:

- If a section contain symbols considered not character by **isCharacter** method, the string returned may stop at the first symbol read. Leaving the description of the section inside the table incomplete.

- The way I "corrected" the link-get problem only works if the actual link for the article is put first before any other link section. If that's not the case, it may get a different link that is not from the article.

- Some of the problems corrected are connected to the fact that the way the XML files were built may vary from RSS Feed to RSS Feed. This project doesn't guarantee that it will be able to read well **EVERY** feed put inside the list by the user.