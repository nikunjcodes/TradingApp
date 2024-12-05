import React from 'react'
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Avatar, AvatarImage } from 'radix-ui'
const Portfolio = () => {
  return (
    <div className='p-5 lg:p-20'>
      <h1 className='font-bold text-2xl pb-5'>Portfolio</h1>
        <Table>
      <TableHeader>
        <TableRow>
          <TableHead className>
            Assest
          </TableHead>
          <TableHead>Price</TableHead>
          <TableHead>Unit</TableHead>
          <TableHead>Change</TableHead>
          <TableHead>Change%</TableHead>
          <TableHead className="text-right">Volume</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1].map((item ,index)=>
        <TableRow key = {index}>
          <TableCell className="font-medium flex items-center gap-2">
            <Avatar className="z-50">
              <AvatarImage src="https://assets.coingecko.com/coins/images/1/standard/bitcoin.png?1696501400" />
            </Avatar>
            <span>
              Bitcoin
            </span>
          </TableCell>
          <TableCell>BTC</TableCell>
          <TableCell>8770302578</TableCell>
          <TableCell>913122234713344</TableCell>
          <TableCell>805526885</TableCell>
          <TableCell className="text-right">$80000.00</TableCell>
        </TableRow>
        )}
      </TableBody>
    </Table>
    </div>
  )
}

export default Portfolio