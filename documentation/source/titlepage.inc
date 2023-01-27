\begin{titlepage}

\thispagestyle{empty}

% side bar
\begin{textblock}{40}(0,0)
\includegraphics[width=40mm,height=300mm]{small_leiste_200dpi.jpg}
\end{textblock}

\begin{textblock}{135}(50,50)
  \makeatletter
  \raggedleft
  {\itshape\LARGE \py@release\releaseinfo \par}
  \vfill
  Generated: \@date \par
  \makeatother
\end{textblock}

\begin{textblock}{135}(50,115)
  \centering
  {\color[gray]{0.5}\rule{\linewidth}{2pt}}\llap{\rule{\linewidth}{0.5pt}}\\
  \vspace{2mm}
  \fontsize{24}{26}\selectfont\sf%
  \makeatletter
  {\Huge\sffamily\@title\par}
  \makeatother
  \vspace{-1mm}
  {\color[gray]{0.5}\rule{\linewidth}{2pt}}\llap{\rule[1.5pt]{\linewidth}{0.5pt}}\\
\end{textblock}

\begin{textblock}{135}(50,180)
  \makeatletter
  \raggedleft
  {\Large\sffamily\@author\par}
  \makeatother
\end{textblock}

% pengutronix logo and address
\begin{textblock}{135}(50,225)
  \raggedleft
    \includegraphics[width=60mm]{new_logo_2006_ptx.pdf}\hspace*{-7pt}\vspace*{-7pt}\\
  \fontsize{12}{12}\selectfont%
  Pengutronix e.\,K.\\
  Peiner Straße 6--8\\
  31137 Hildesheim\\[1ex]
  +49\,(0)51\,21\,/\,20\,69\,17\,--\,0 (Fon)\\
  +49\,(0)51\,21\,/\,20\,69\,17\,--\,55\,55 (Fax)\\[1ex]
  info@pengutronix.de\\[2.5ex]
  {\footnotesize \copyright \, \number \year \ Pengutronix, Hildesheim \negthinspace}
\end{textblock}

\end{titlepage}
\vspace*{\fill}
\newpage
% vim:syntax=tex
