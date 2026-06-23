// Define o pacote (endereço lógico) do seu aplicativo dentro do Android Studio.
package com.example.miniaplicativos

// IMPORTAÇÕES: Trazem as ferramentas do Android, Kotlin e Jetpack Compose necessárias para o código funcionar.
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==========================================
// ETAPA 1: O Enum de Rotas [cite: 11]
// ==========================================
// Um 'enum class' serve para criar uma lista fixa de opções aceitáveis.
// Aqui, ele define quais são as 4 telas possíveis no nosso sistema[cite: 12].
enum class Tela {
    MENU, CONTADOR, CONVERSOR, DADO
}

// Classe principal que inicializa o aplicativo Android[cite: 98].
class MainActivity : ComponentActivity() {
    // Método 'onCreate' é o ponto de partida do ciclo de vida do app (quando ele abre)[cite: 99].
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 'setContent' define que a interface deste app será criada com o Jetpack Compose[cite: 101].
        setContent {

            // ESTADO DE NAVEGAÇÃO: Cria uma variável reativa 'telaAtual' que começa apontando para o MENU[cite: 102, 103].
            // O 'by remember' faz o Android lembrar desse valor mesmo se a tela girar ou atualizar[cite: 7].
            // O 'mutableStateOf' avisa ao Compose para redesenhar a tela sempre que o valor mudar[cite: 8].
            var telaAtual by remember { mutableStateOf(Tela.MENU) }

            // ORQUESTRADOR DE TELAS (Navegação Reativa): Um 'when' (equivalente ao switch) que analisa qual tela está ativa[cite: 104].
            when (telaAtual) {
                // Se for MENU, desenha a TelaMenu e passa uma função que muda o estado para o destino escolhido[cite: 105].
                Tela.MENU -> TelaMenu(onNavegar = { destino -> telaAtual = destino })

                // Se for CONTADOR, CONVERSOR ou DADO, desenha a respectiva tela.
                // O parâmetro 'onVoltar' passa uma função que faz a variável voltar a ser 'Tela.MENU'[cite: 106, 107, 108].
                Tela.CONTADOR -> TelaContador(onVoltar = { telaAtual = Tela.MENU })
                Tela.CONVERSOR -> TelaConversor(onVoltar = { telaAtual = Tela.MENU })
                Tela.DADO -> TelaDado(onVoltar = { telaAtual = Tela.MENU })
            }
        }
    }
}

// ==========================================
// ETAPA 2: Tela Inicial (Menu) [cite: 16]
// ==========================================
// '@Composable' avisa ao compilador que esta função gera elementos visuais na tela[cite: 20].
// 'onNavegar' é um evento (callback) que avisa ao MainActivity quando o usuário quer mudar de tela[cite: 17, 21].
@Composable
fun TelaMenu(onNavegar: (Tela) -> Unit) {
    // 'Column' organiza os elementos de forma vertical (um embaixo do outro)[cite: 22].
    Column(
        // 'Modifier' configura o visual e comportamento do container[cite: 24].
        modifier = Modifier
            .fillMaxSize() // Faz a coluna ocupar toda a largura e altura da tela do celular[cite: 25].
            .background(Color(0xFFF8FAFC)) // Define uma cor de fundo cinza azulado claro[cite: 26].
            .padding(24.dp), // Cria um espaçamento interno nas bordas da tela[cite: 26].
        horizontalAlignment = Alignment.CenterHorizontally, // Centraliza todos os itens na horizontal[cite: 27].
        verticalArrangement = Arrangement.Center // Centraliza o bloco de itens na vertical[cite: 27].
    ) {
        // Título principal do menu [cite: 28]
        Text(
            text = "Hub de Exercícios Compose", // O texto que aparece [cite: 30]
            fontSize = 22.sp, // Tamanho da fonte [cite: 30]
            fontWeight = FontWeight.Bold, // Estilo em Negrito [cite: 31]
            color = Color(0xFF1E3A8A) // Cor azul escura [cite: 32]
        )

        // 'Spacer' serve puramente para dar um espaço em branco vertical entre os componentes[cite: 33].
        Spacer(modifier = Modifier.height(32.dp))

        // ----------------- BOTÃO DO APP 1: CONTADOR -----------------
        Button(
            // Ao ser clicado, ele executa a função 'onNavegar' passando a rota do CONTADOR[cite: 36].
            onClick = { onNavegar(Tela.CONTADOR) },
            modifier = Modifier.fillMaxWidth(0.8f) // Ocupa 80% da largura máxima da tela [cite: 36]
        ) {
            Text("1. Contador de Cliques") // Texto dentro do botão [cite: 38]
        }

        Spacer(modifier = Modifier.height(12.dp)) // Espaço entre os botões [cite: 40]

        // ----------------- BOTÃO DO APP 2: CONVERSOR -----------------
        Button(
            onClick = { onNavegar(Tela.CONVERSOR) }, // Altera o estado para abrir o conversor [cite: 43]
            modifier = Modifier.fillMaxWidth(0.8f),
            // Altera a cor padrão do botão para Azul Claro [cite: 44]
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
        ) {
            Text("2. Conversor de Temperatura")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ----------------- BOTÃO DO APP 3: DADO VIRTUAL -----------------
        Button(
            onClick = { onNavegar(Tela.DADO) }, // Altera o estado para abrir o dado [cite: 52]
            modifier = Modifier.fillMaxWidth(0.8f),
            // Altera a cor padrão do botão para Verde [cite: 54]
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669))
        ) {
            Text("3. Dado Virtual")
        }
    }
}

// ==========================================
// ETAPA 3: Aplicativo 1 - Contador de Cliques [cite: 67]
// ==========================================
@Composable
fun TelaContador(onVoltar: () -> Unit) {
    // ESTADO LOCAL: Cria e lembra uma variável inteira que guarda a quantidade de cliques[cite: 71].
    var cliques by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // CORRIGIDO AQUI: Ajustado a atribuição correta dos parâmetros removendo os símbolos de $
        Text(text = "Contador de Cliques", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Exibe o número de cliques atual. A expressão '$cliques' injeta o valor da variável no texto[cite: 83].
        Text(text = "Cliques: $cliques", fontSize = 36.sp, color = Color(0xFF3B82F6))

        Spacer(modifier = Modifier.height(24.dp))

        // Botão de ação do app [cite: 85]
        Button(onClick = { cliques++ }) { // 'cliques++' soma +1 ao estado atual, disparando o redesenho do texto [cite: 85]
            Text("Incrementar (+1)")
        }

        Spacer(modifier = Modifier.height(48.dp))

        // 'OutlinedButton' é um botão apenas com borda, excelente para ações secundárias (como Voltar)[cite: 90].
        OutlinedButton(onClick = onVoltar) { // Aciona o callback que joga o usuário de volta para o menu [cite: 90]
            Text("Voltar ao Menu")
        }
    }
}

// ==========================================
// DESAFIO 1: Conversor de Temperatura [cite: 121]
// ==========================================
@Composable
fun TelaConversor(onVoltar: () -> Unit) {
    // ESTADOS LOCAIS:
    // 'campoCelsius' guarda em tempo real o texto que o usuário digita na caixa[cite: 129, 130].
    var campoCelsius by remember { mutableStateOf("") }
    // 'resultadoFahrenheit' guarda a resposta do cálculo para exibir na tela[cite: 129, 130].
    var resultadoFahrenheit by remember { mutableStateOf("Digite um valor") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Conversor de Temperatura", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        // 'OutlinedTextField' é a caixa de digitação/input de texto do Material Design 3[cite: 124].
        OutlinedTextField(
            value = campoCelsius, // Mostra o valor atual do estado [cite: 130]
            onValueChange = { campoCelsius = it }, // Atualiza o estado com o novo caractere digitado [cite: 130]
            label = { Text("Graus Celsius (°C)") }, // Texto de instrução flutuante dentro do campo
            // 'KeyboardOptions' força o teclado do celular a abrir apenas no modo numérico [cite: 132]
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão que aciona o cálculo de conversão [cite: 125]
        Button(
            onClick = {
                // 'toDoubleOrNull()' tenta transformar o texto em número decimal.
                // Se a caixa estiver vazia ou com texto inválido, retorna null evitando que o app trave (crash)[cite: 132, 133].
                val celsius = campoCelsius.toDoubleOrNull()

                if (celsius != null) {
                    // Aplica a fórmula matemática de conversão: F = (C * 1.8) + 32 [cite: 131]
                    val fahrenheit = (celsius * 1.8) + 32
                    // '.format(fahrenheit)' limita o resultado para apenas 2 casas decimais (ex: 75.32)
                    resultadoFahrenheit = "Resultado: %.2f °F".format(fahrenheit)
                } else {
                    // Mensagem preventiva caso o usuário burle as regras
                    resultadoFahrenheit = "Por favor, digite um número válido!"
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
        ) {
            Text("Converter para Fahrenheit")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Componente que exibe na tela a string calculada do resultado [cite: 126]
        Text(text = resultadoFahrenheit, fontSize = 22.sp, fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(48.dp))

        // Botão para retornar ao menu inicial [cite: 127]
        OutlinedButton(onClick = onVoltar) {
            Text("Voltar ao Menu")
        }
    }
}

// ==========================================
// DESAFIO 2: Dado Virtual [cite: 136]
// ==========================================
@Composable
fun TelaDado(onVoltar: () -> Unit) {
    // ESTADO LOCAL: Armazena o número atual exibido pelo dado (inicializa em 1)[cite: 144, 145].
    var numeroDado by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Texto de cabeçalho [cite: 139]
        Text(text = "Role o Dado Virtual!", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(32.dp))

        // TEXTO DE DESTAQUE: Mostra o número atual do dado com fonte bem grande (72sp)[cite: 140].
        Text(
            text = numeroDado.toString(), // Converte o Int para String para poder exibir
            fontSize = 72.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF059669) // Cor verde correspondente ao botão
        )

        Spacer(modifier = Modifier.height(16.dp))

        // DESAFIO EXTRA (Condicional): Se o estado 'numeroDado' for rigorosamente igual a 6[cite: 147],
        // o Compose renderiza dinamicamente esse bloco de texto comemorativo.
        if (numeroDado == 6) {
            Text(
                text = "🎉 Incrível! Você tirou o número máximo! 🎉",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD97706) // Cor dourada/laranja para comemoração
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botão principal para sortear o número [cite: 141]
        Button(
            onClick = {
                // '(1..6).random()' define um intervalo fechado de 1 a 6 e escolhe um valor aleatório nativamente[cite: 146].
                // Ao atribuir esse valor para 'numeroDado', a tela sofre uma recomposição imediata.
                numeroDado = (1..6).random()
            },
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669))
        ) {
            Text("Rolar Dado")
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Botão para retornar ao menu principal [cite: 142]
        OutlinedButton(onClick = onVoltar) {
            Text("Voltar ao Menu")
        }
    }
}