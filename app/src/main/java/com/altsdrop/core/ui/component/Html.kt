package com.altsdrop.core.ui.component

fun getHtmlText(
    content: String,
    backgroundColor: String = "#FFFFFF",
    bodyColor: String = "#000000"
) = """
    <html>
        <head>
            <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
            <style>
                body {
                    margin: 0;
                    padding: 0;
                    background: $backgroundColor !important;
                    color: $bodyColor !important;
                    font-family: 'Poppins' !important;
                }
                a, a:link, a:visited, a:hover, a:active {
                    color: #3179ed !important;
                    text-decoration: none !important;
                }
            </style>
        </head>
        <body>
           $content
        </body>
    </html>
    """.trimIndent()