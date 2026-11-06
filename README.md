# Lloguer de Videojocs
Aplicació desenvolupada amb Java Swing per gestionar el lloguer de videojocs: usuaris, jocs, plataformes, estoc i lloguers.

## Funcionalitats
- Finestra principal amb una taula que conté tots els lloguers: número de registre, joc, usuari, plataforma, data d'inici del lloguer, data de tornada i gènere del joc.
- Ordenació de la taula per número de registre en ordre descendent i comptador total de lloguers.
- Colors de la fila:
  -   **Verd**: lloguer retornat
  -   **Vermell**: lloguer vençut però encara actiu.
- Registrar un lloguer amb usuari, joc i plataforma.
- Consultar les unitats disponibles a cada plataforma abans de confirmar el lloguer.
- Tornar un lloguer actiu i restaurar automàticament l'estoc.
- Crear i esborrar usuaris.
- Crear i esborrar jocs.
- Interfície en català, amb textes centralitzats.

## Regles
- La data de inici d'un lloguer no es pot modificar i la tornada es calcula automàticament a 7 dies vista.
- Un lloguer està vençut si continua actiu i la data actual supera la data prevista de tornada.
- No es pot registrar un lloguer sense estoc disponible. (radio buttons desactivats)
- No es pot registrar un lloguer actiu del mateix usuari, joc i plataforma.
- En tornar un lloguer, s'augmenta una unitat de l'estoc.
- El DNI de l'usuari ha de ser únic, alfanumèric i màxim de 9 caracters.
- EL nom de l'usuari ha de començar amb majúscula.
- El telèfon només pot contenir dígits i té un màxim de 15 caràcters.

## Estructura del projecte
```text
src/main/java/
              model/         -- GestorDades, Joc, Lloguer, Usuari, Plataforma, gèneres i excepcions
              validationapi/ -- ValidationAPI i ValidationException
              vista/         -- FinestraPrincipal, Textes i diàlegs de l'aplicació
src/main/resources/
              Bundle_ca.properties
pom.xml
```

## Com executar
Requisits: JDK 24 o superior i Maven.
Descarregar el ZIP del projecte.

Des de NetBeans:

1. Obre el projecte.
2. Executa'l amb **Run Project** (`F6`).
3. La classe principal és `vista.FinestraPrincipal`.

## Dades de prova

En iniciar l'aplicació es carreguen:

- 2 usuaris: Marc Rosselló Puig i Laia Serra Vidal.
- 2 jocs: The Legend of Zelda i Forza Horizon.
- 2 lloguers inicials, amb l'estoc ja descomptat.
- Un dels lloguers està vençut a propòsit per comprovar la fila vermella.

## Notes

- Les dades es mantenen en memòria: en tancar l'aplicació es reinicien.
- Els textos de la interfície es troben a `Bundle_ca.properties`, guardat en UTF-8.
- Durant el desenvolupament vaig tenir problemes per vincular el projecte de NetBeans amb GitHub. En un principi, la integració automàtica des de NetBeans no va funcionar correctament. Vaig continuar fent l'exercici i finalment vaig trobar l'origen de l'error.
