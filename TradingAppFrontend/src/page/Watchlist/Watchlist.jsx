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
import { Button } from '@/components/ui/button'
import { BookmarkFilledIcon } from '@radix-ui/react-icons'
const Watchlist = () => {
  const handleRemoveToWatchlist=(value)=>{
    console.log('Remove')
  }
  return (
    <div className='p-5 lg:p-20'>
    <h1 className='font-bold text-2xl pb-5'>Watchlist</h1>
    <Table className="border">
      <TableHeader>
        <TableRow>
          <TableHead className="py-5">
            Coin
          </TableHead>
          <TableHead>Symbol</TableHead>
          <TableHead>Volume</TableHead>
          <TableHead>Market Cap</TableHead>
          <TableHead>24H</TableHead>
          <TableHead className>Price</TableHead>
          <TableHead className="text-right text-red-600">Remove</TableHead>

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
          <TableCell className="text-right">
            <Button  variant = "ghost" size="icon" className = "h-10 w-10" onClick={()=>handleRemoveToWatchlist(item.id)}>
              <BookmarkFilledIcon className='w-6 h-6'/>
            </Button>
          </TableCell>

        </TableRow>
        )}
      </TableBody>
    </Table>
  </div>
  )
}

export default Watchlist