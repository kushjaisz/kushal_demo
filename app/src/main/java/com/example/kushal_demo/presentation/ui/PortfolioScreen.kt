import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kushal_demo.domain.model.Holding
import com.example.kushal_demo.presentation.viewmodel.PortfolioViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioScreen(viewModel: PortfolioViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        TopAppBar(title = { Text("Portfolio") })

        TabRow(selectedTabIndex = 1) {
            Tab(selected = true, onClick = {}, text = { Text("POSITIONS") })
            Tab(selected = false, onClick = {}, text = { Text("HOLDINGS") })
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            items(state) { item ->
                StockItem(item)
                HorizontalDivider()
            }
        }

        SummarySection(
            state,
            expanded = isExpanded,
            onToggleExpand = { isExpanded = !isExpanded }
        )
    }
}

@Composable
fun StockItem(item: Holding) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                item.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        append("LTP: ")
                    }
                    withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Medium)) {
                        append("₹${String.format("%.3f", item.currentPrice)}")
                    }
                },
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        append("Net Qty: ")
                    }
                    withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Medium)) {
                        append("${String.format("%.3f", item.quantity)}")
                    }
                },
                fontSize = 16.sp
            )

            val pnlColor = if (item.pnl >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        append("P&L: ")
                    }
                    withStyle(style = SpanStyle(color = pnlColor, fontWeight = FontWeight.SemiBold)) {
                        append("₹${String.format("%.3f", item.pnl)}")
                    }
                },
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun SummarySection(
    holdings: List<Holding>,
    expanded: Boolean,
    onToggleExpand: () -> Unit
) {
    val totalCurrentValue = holdings.sumOf { it.currentValue }
    val totalInvestment = holdings.sumOf { it.investment }
    val totalPNL = totalCurrentValue - totalInvestment
    val todaysPNL = holdings.sumOf { it.todayPnl }

    val pnlColor = if (totalPNL >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        if (expanded) {
            SummaryRow(
                label = "Current value*",
                value = "₹ ${String.format("%.2f", totalCurrentValue)}",
                labelColor = Color(0xFF4CAF50)
            )
            SummaryRow(
                label = "Total investment*",
                value = "₹ ${String.format("%.2f", totalInvestment)}",
                labelColor = Color.Gray
            )
            SummaryRow(
                label = "Today’s Profit & Loss*",
                value = "₹ ${String.format("%.2f", todaysPNL)}",
                labelColor = Color.Gray,
                valueColor = if (todaysPNL >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = "Profit & Loss*",
                    color = Color.Gray,
                    fontSize = 14.sp
                )


                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Toggle Summary",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onToggleExpand() }
                        .padding(start = 4.dp)
                )

            }

            Text(
                text = "₹ ${String.format("%.2f", totalPNL)} (${String.format("%.2f", (totalPNL / totalInvestment) * 100)}%)",
                color = pnlColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



@Composable
fun SummaryRow(
    label: String,
    value: String,
    labelColor: Color = Color.Black,
    valueColor: Color = Color.Black
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = labelColor, fontSize = 14.sp)
        Text(text = value, color = valueColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}



