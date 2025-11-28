# 🚐 App Van --- README Oficial (Atualizado)

Este documento descreve toda a estrutura do projeto **App Van**,
incluindo **código Kotlin**, **ViewModels**, **Fragments**, **DBHelper**
e agora também toda a parte de **XML layouts** localizada em
`res/layout`.

------------------------------------------------------------------------

## 📁 Estrutura Completa do Projeto

A arquitetura segue MVVM com separação clara entre UI, dados e lógica.

    ui/
     ├── data/
     │    └── DBHelper.kt
     │
     ├── alunos/
     │    ├── Aluno.kt
     │    ├── AlunoAdapter.kt
     │    ├── AlunosFragment.kt
     │    └── AlunosViewModel.kt
     │
     ├── escola/
     │    ├── Escola.kt
     │    ├── EscolaAdapter.kt
     │    ├── EscolaFragment.kt
     │    └── EscolaViewModel.kt
     │
     ├── equipe/
     │    ├── Equipe.kt
     │    ├── EquipeAdapter.kt
     │    ├── EquipeFragment.kt
     │    └── EquipeViewModel.kt
     │
     ├── gallery/
     │    ├── GalleryFragment.kt
     │    └── GalleryViewModel.kt
     │
     ├── home/
     │    ├── HomeFragment.kt
     │    └── HomeViewModel.kt
     │
     ├── slideshow/
     │    ├── SlideshowFragment.kt
     │    └── SlideshowViewModel.kt
     │
     └── MainActivity.kt

------------------------------------------------------------------------

# 🎨 Layouts XML (res/layout)

A pasta **layout/** contém todos os XML utilizados pela interface do
app:

    res/layout/
     ├── activity_main.xml
     ├── app_bar_main.xml
     ├── content_main.xml
     │
     ├── dialog_cadastro_equipe.xml
     │
     ├── fragment_alunos.xml
     ├── fragment_equipe.xml
     ├── fragment_escola.xml
     ├── fragment_gallery.xml
     ├── fragment_home.xml
     ├── fragment_slideshow.xml
     │
     ├── item_aluno.xml
     ├── item_equipe.xml
     ├── item_escola.xml
     │
     └── nav_header_main.xml

### ✔ Telas principais (Fragments)

-   `fragment_alunos.xml` → UI da lista e cadastro de alunos\
-   `fragment_escola.xml` → UI das escolas cadastradas\
-   `fragment_equipe.xml` → UI para equipes\
-   `fragment_home.xml`, `fragment_gallery.xml`,
    `fragment_slideshow.xml` → Telas padrão do template

### ✔ Dialogs

-   `dialog_cadastro_equipe.xml` → Formulário modal para nova equipe

### ✔ Itens de RecyclerView

-   `item_aluno.xml`\
-   `item_equipe.xml`\
-   `item_escola.xml`

### ✔ Layouts base

-   `activity_main.xml`\
-   `app_bar_main.xml`\
-   `content_main.xml`\
-   `nav_header_main.xml`

------------------------------------------------------------------------

# 🗄️ Banco de Dados --- DBHelper

O arquivo **DBHelper.kt**, localizado em `ui/data`, gerencia:

### ✔ Criação e atualização de tabelas (onCreate / onUpgrade)

-   Alunos\
-   Escolas\
-   Equipes

### ✔ CRUD completo

-   Inserir\
-   Atualizar\
-   Deletar\
-   Buscar registros

### ✔ Conexão centralizada

Todos os ViewModels das features usam o DBHelper como fonte única de
persistência.

------------------------------------------------------------------------

# 🧩 Padrão das Features (MVVM)

Cada pasta (alunos, escola, equipe) contém:

  Tipo             Função
  ---------------- ------------------------------------------------
  `Model.kt`       Representa a entidade do banco
  `Adapter.kt`     Exibição em listas (RecyclerView)
  `Fragment.kt`    Tela e lógica de UI
  `ViewModel.kt`   Camada intermediária que conversa com DBHelper

------------------------------------------------------------------------

# ▶️ Como rodar

1.  Abrir no Android Studio\
2.  Sincronizar Gradle\
3.  Rodar em um dispositivo físico ou emulador

------------------------------------------------------------------------

# 🎯 Objetivo do App

O App Van gerencia:

-   Alunos transportados\
-   Escolas atendidas\
-   Equipes de transporte

Permitindo controle simples e organizado dos dados.

------------------------------------------------------------------------

# 👨‍💻 Autor

Desenvolvido em Kotlin utilizando MVVM e SQLite.
