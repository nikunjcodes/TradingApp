import React, { useEffect } from "react";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Avatar, AvatarImage } from "radix-ui";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { getCoinList } from "@/State/Coin/Action";

const AssetTable = ({ coin, category }) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead className="w-[100px]">Coin</TableHead>
          <TableHead>Symbol</TableHead>
          <TableHead>Volume</TableHead>
          <TableHead>Market Cap</TableHead>
          <TableHead>24H</TableHead>
          <TableHead className="text-right">Price</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {coin.map((item, index) => (
          <TableRow key={item.id}>
            <TableCell
              onClick={() => navigate("/market/bitcoin")}
              stock-className="font-medium flex items-center gap-2"
            >
              <Avatar className="z-50">
                <AvatarImage src={item.image} />
              </Avatar>
              <span>Bitcoin</span>
            </TableCell>
            <TableCell>BTC</TableCell>
            <TableCell>8770302578</TableCell>
            <TableCell>913122234713344</TableCell>
            <TableCell>805526885</TableCell>
            <TableCell className="text-right">$80000.00</TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
};

export default AssetTable;
