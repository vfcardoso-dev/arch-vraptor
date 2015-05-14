# README #

A arch-vraptor é uma biblioteca simples criada por mim para auxiliar nas tarefas recorrentes nos projetos que eu desenvolvo utilizando o VRaptor. Caso você ache ela útil, fique a vontade para usá-la e extendê-la da maneira que achar melhor.

Atualmente ela está adaptada para funcionar com a versão 3.5.x do VRaptor. Em breve criarei outra versão para a versão 4.x.

Para utilizá-la, basta compilar um jar com o código-fonte do repositório, adicionar esse jar no classpath, e adicionar o seguinte trecho no arquivo web.xml.

```
#!xml

<context-param>
    <param-name>br.com.caelum.vraptor.packages</param-name>
    <param-value>
        com.viniciuscardoso.arch.vraptor
    </param-value>
</context-param>

```

Caso seu arquivo web.xml já possua a tag <context-param>, você deve adicionar o valor separado por vírgula.


```
#!xml

<context-param>
    <param-name>br.com.caelum.vraptor.packages</param-name>
    <param-value>
        br.com.caelum.vraptor.converter.jodatime,
        br.com.caelum.vraptor.converter.l10n,
        com.viniciuscardoso.arch.vraptor
    </param-value>
</context-param>

```

That's all, folks! =)