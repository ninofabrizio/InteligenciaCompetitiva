#A (simple) Web Crawler in Java

The objective is to make a Web Crawler that gets the content of a link and stores it's body in a text file. Then it stores different links that the original one makes reference in a different text file. In the end, the text file with the body will contain all the info it got previously organized from all the sites crawled.

Made using the Eclipse IDE in Java 1.8.0.

---------------------------------------------------------------------------
## Known issues:

- It's possible for some not actual links (ex., ending in .m4a) getting added to the list.